package com.course.modularfoodcatalog.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.course.core.data.Receipes
import com.course.core.data.Repository
import com.course.core.data.local.CartRepository
import com.course.core.data.local.RecipesEntity
import com.course.core.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 *hrahm,23/04/2024, 18:59
 **/
class MainViewModel(
    private val repository: Repository,
    application: Application,
    private val cartRepository: CartRepository
) :
    BaseViewModel(application) {


    val _uiStateReceipeList = MutableStateFlow<UiState<Receipes>>(UiState.Loading)
    val uiStateReceipeList: StateFlow<UiState<Receipes>> = _uiStateReceipeList

    val _uiStateReceipeDetail = MutableStateFlow<UiState<Receipes.Recipe>>(UiState.Loading)
    val uiStateReceipeDetail: StateFlow<UiState<Receipes.Recipe>> = _uiStateReceipeDetail

    val _uiStateReceipeEntity = MutableStateFlow<UiState<RecipesEntity>>(UiState.Loading)
    val uiStateReceipeEntity: StateFlow<UiState<RecipesEntity>> = _uiStateReceipeEntity
    fun getReceipesList() = viewModelScope.launch {
        repository.getReceipes(context).collect {
            when (it) {
                is UiState.Success -> {
                    _uiStateReceipeList.value = UiState.Success(it.data)
                }

                is UiState.Loading -> {
                    _uiStateReceipeList.value = UiState.Loading
                }

                is UiState.Error -> {
                    //Handle Error
                    _uiStateReceipeList.value = UiState.Error(it.message)
                }
            }
        }
    }

    fun getReceipeDetail(id: Int?) = viewModelScope.launch {
        repository.getReceipesDetail(context, id).collect {
            when (it) {
                is UiState.Success -> {
                    _uiStateReceipeDetail.value = UiState.Success(it.data)
                }

                is UiState.Loading -> {
                    _uiStateReceipeDetail.value = UiState.Loading
                }

                is UiState.Error -> {
                    //Handle Error
                    _uiStateReceipeDetail.value = UiState.Error(it.message)
                }
            }
        }
    }
    fun setCurrentRecipesEntity(recipesEntity: RecipesEntity) {
        _uiStateReceipeEntity.value = UiState.Success(recipesEntity)
    }
    fun saveCart() {
        viewModelScope.launch {
            val currentRecipesEntity = (_uiStateReceipeEntity.value as? UiState.Success)?.data
            if (currentRecipesEntity != null) {
                try {
                    cartRepository.saveRecipes(currentRecipesEntity)
                    _uiStateReceipeEntity.value = UiState.Success(currentRecipesEntity)
                } catch (e: Exception) {
                    _uiStateReceipeEntity.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
            } else {
                _uiStateReceipeEntity.value = UiState.Error("No RecipesEntity available to save")
            }
        }
    }


}