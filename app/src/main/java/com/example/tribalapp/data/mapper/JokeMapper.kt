package com.example.tribalapp.data.mapper

import com.example.tribalapp.data.local.entity.JokeEntity
import com.example.tribalapp.data.remote.dto.JokeDTO
import com.example.tribalapp.domain.model.JokeModel
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

class JokeMapper @Inject constructor(): Mapper<JokeDTO, JokeEntity, JokeModel> {
    override fun dtoToEntity(input: JokeDTO): JokeEntity {
        return JokeEntity(input.id,input.icon_url,input.url,input.value, input.createdAt, input.updatedAt, input.categories.get(0))
    }
    override fun entityToModel(input: JokeEntity): JokeModel {
        return JokeModel(input.id,input.icon_url,input.url,input.value, input.createdAt, input.updatedAt, input.category)
    }
}