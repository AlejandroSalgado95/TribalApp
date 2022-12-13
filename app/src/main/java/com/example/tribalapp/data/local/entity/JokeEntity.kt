package com.example.tribalapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(
    tableName = "joke_table"
)
data class JokeEntity (
    @PrimaryKey
    val id: String = "",
    val icon_url: String = "",
    val url: String = "",
    val value: String,
    val createdAt: String = "",
    val updatedAt: String = "",
    val category: String = "" ,
)