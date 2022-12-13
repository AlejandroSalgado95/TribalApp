package com.example.tribalapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JokeModel(
    val id: String = "",
    val icon_url: String = "",
    val url: String = "",
    val value: String,
    val createdAt: String = "",
    val updatedAt: String = "",
    val category: String = ""
) : Parcelable