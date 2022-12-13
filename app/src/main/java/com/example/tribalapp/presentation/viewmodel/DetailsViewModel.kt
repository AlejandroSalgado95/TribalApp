package com.example.tribalapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tribalapp.domain.model.JokeModel
import com.example.tribalapp.domain.repository.Repository
import com.example.tribalapp.utils.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val state: SavedStateHandle
) : ViewModel() {

    val category = state.get<String>("category")?: ""

    val jokeState: MutableLiveData<UiState> by lazy { MutableLiveData() }
    val jokeLocalList = repository.getJokesOfCategoryFromDB(category)

    init {
            getRandomJokeOfCategory()
    }

    fun getRandomJokeOfCategory() {
        viewModelScope.launch {
            jokeState.postValue(UiState.IsLoading)
            val result = repository.getRandomJokeOfCategory(category)
            when (result) {
                is Resource.Loading ->{
                    //NO-OP//
                }
                is Resource.Success -> {
                    jokeState.postValue(UiState.Data(null))
                }
                is Resource.Error -> {
                    jokeState.postValue(UiState.IsError)
                }

            }
        }
    }

    sealed class UiState {
        object IsLoading : UiState()
        object IsError : UiState()
        data class Data(val data: JokeModel?) : UiState()
    }

}