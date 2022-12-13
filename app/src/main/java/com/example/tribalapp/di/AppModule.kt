package com.example.tribalapp.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.tribalapp.R
import com.example.tribalapp.data.local.TribalDao
import com.example.tribalapp.data.local.TribalDatabase
import com.example.tribalapp.data.remote.ChuckNorrisApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJokeApi(): ChuckNorrisApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(ChuckNorrisApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()
            .create(ChuckNorrisApi::class.java)
    }


    @Singleton
    @Provides
    fun provideTribalDataBase(@ApplicationContext context: Context): TribalDatabase {
        return Room.databaseBuilder(context, TribalDatabase::class.java, "tribal_db.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideTribalDao(database: TribalDatabase): TribalDao {
        return database.getTribalDao()
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
        )
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

}