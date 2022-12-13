package com.example.tribalapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.tribalapp.domain.model.JokeModel
import com.example.tribalapp.utils.Resource

interface Repository {
    suspend fun getRandomJokeOfCategory(category : String): Resource<JokeModel>
    suspend fun getCategories(): Resource<List<String>>
    fun getJokesOfCategoryFromDB(category:String): LiveData<List<JokeModel>>
}