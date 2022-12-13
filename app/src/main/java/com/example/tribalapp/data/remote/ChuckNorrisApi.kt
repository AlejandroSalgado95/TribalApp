package com.example.tribalapp.data.remote

import com.example.tribalapp.data.remote.dto.JokeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChuckNorrisApi {
    @GET("random")
    suspend fun getRandomJokeOfCategory(@Query(value = "category", encoded = true) category : String): Response<JokeDTO>

    @GET("categories")
    suspend fun getCategories(): Response<List<String>>

    companion object {
        const val BASE_URL = "https://api.chucknorris.io/jokes/"
    }
}