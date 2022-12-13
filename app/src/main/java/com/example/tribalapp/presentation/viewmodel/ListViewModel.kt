package com.example.tribalapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.tribalapp.domain.model.JokeModel
import com.example.tribalapp.domain.repository.Repository
import com.example.tribalapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val categoriesState: MutableLiveData<UiState> by lazy { MutableLiveData() }

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            categoriesState.postValue(UiState.IsLoading)
            val result = repository.getCategories()
            when (result) {
                is Resource.Loading ->{
                    //NO-OP//
                }
                is Resource.Success -> {
                    categoriesState.postValue(UiState.Data(result.data))
                }
                is Resource.Error -> {
                    categoriesState.postValue(UiState.IsError)
                }

            }
        }
    }

    sealed class UiState {
        object IsLoading : UiState()
        object IsError : UiState()
        data class Data(val data: List<String>?) : UiState()
    }
}