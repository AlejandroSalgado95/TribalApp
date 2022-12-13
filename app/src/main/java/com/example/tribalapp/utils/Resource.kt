package com.example.tribalapp.utils


sealed class Resource<T>(val data : T? = null){
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null) : Resource<T>(data)
    class Loading<T> : Resource<T>()
}