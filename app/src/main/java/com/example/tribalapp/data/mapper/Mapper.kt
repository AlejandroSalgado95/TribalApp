package com.example.tribalapp.data.mapper

interface Mapper<DTO, Entity, Model > {
    fun dtoToEntity(input: DTO): Entity
    fun entityToModel(input: Entity): Model
}