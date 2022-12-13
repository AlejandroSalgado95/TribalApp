package com.example.tribalapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.tribalapp.data.local.TribalDao
import com.example.tribalapp.data.local.entity.JokeEntity
import com.example.tribalapp.data.mapper.Mapper
import com.example.tribalapp.data.remote.ChuckNorrisApi
import com.example.tribalapp.data.remote.dto.JokeDTO
import com.example.tribalapp.domain.model.JokeModel
import com.example.tribalapp.domain.repository.Repository
import com.example.tribalapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val remoteDataSource: ChuckNorrisApi,
    private val localDataSource: TribalDao,
    private val jokeMapper: Mapper<JokeDTO, JokeEntity, JokeModel>
) : Repository {

    override suspend fun getRandomJokeOfCategory(category:String): Resource<JokeModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getRandomJokeOfCategory(category)
                var jokeModel: JokeModel? = null
                response.body()?.let {
                    var jokeEntity: JokeEntity = jokeMapper.dtoToEntity(it)
                    localDataSource.insertJoke(jokeEntity)
                    jokeModel = jokeMapper.entityToModel(jokeEntity)
                }
                Resource.Success(jokeModel)
            } catch (e: HttpException) {
                Resource.Error()
            } catch (e: IOException) {
                Resource.Error()
            } catch (e: SocketTimeoutException) {
                Resource.Error()
            }
        }
    }

    override suspend fun getCategories(): Resource<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getCategories()
                var listOfCategories = listOf<String>()
                response.body()?.let {
                    listOfCategories = it
                }
                Resource.Success(listOfCategories)
            } catch (e: HttpException) {
                Resource.Error()
            } catch (e: IOException) {
                Resource.Error()
            } catch (e: SocketTimeoutException) {
                Resource.Error()
            }
        }
    }

    override fun getJokesOfCategoryFromDB(category: String): LiveData<List<JokeModel>> {
        val result = localDataSource.getJokesOfCategoryFromDB(category)
        val resultToDomain: LiveData<List<JokeModel>> =
            Transformations.map(result) { it.map { jokeMapper.entityToModel(it) } }
        return resultToDomain
    }
}