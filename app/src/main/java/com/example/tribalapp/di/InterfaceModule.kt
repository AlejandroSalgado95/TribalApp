package com.example.tribalapp.di

import com.example.tribalapp.data.local.entity.JokeEntity
import com.example.tribalapp.data.mapper.JokeMapper
import com.example.tribalapp.data.mapper.Mapper
import com.example.tribalapp.data.remote.dto.JokeDTO
import com.example.tribalapp.data.repository.RepositoryImpl
import com.example.tribalapp.domain.model.JokeModel
import com.example.tribalapp.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class InterfaceModule {
    @Binds
    @ViewModelScoped
    abstract fun bindJokeMapper(jokeMapper: JokeMapper): Mapper<JokeDTO, JokeEntity, JokeModel>

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(RepositoryImpl: RepositoryImpl): Repository
}