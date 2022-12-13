package com.example.tribalapp.data.remote.dto

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class JokeDTO(
    @PrimaryKey
    @SerializedName("id") val id: String = "",
    @SerializedName("icon_url") val icon_url: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("value") val value: String,
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("categories") val categories: List<String> = listOf(),
)