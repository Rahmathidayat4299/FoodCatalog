package com.course.modularfoodcatalog.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.course.core.data.Receipes
import com.course.core.data.domain.FoodUseCase
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
    application: Application,
    private val useCase: FoodUseCase
) :
    BaseViewModel(application) {


    val _uiStateReceipeList = MutableStateFlow<UiState<Receipes>>(UiState.Loading)
    val uiStateReceipeList: StateFlow<UiState<Receipes>> = _uiStateReceipeList

    val _uiStateReceipeDetail = MutableStateFlow<UiState<Receipes.Recipe>>(UiState.Loading)
    val uiStateReceipeDetail: StateFlow<UiState<Receipes.Recipe>> = _uiStateReceipeDetail

    val _uiStateReceipeEntity = MutableStateFlow<UiState<RecipesEntity>>(UiState.Loading)
    val uiStateReceipeEntity: StateFlow<UiState<RecipesEntity>> = _uiStateReceipeEntity

    val _uiStateReceipeEntityList = MutableStateFlow<UiState<List<RecipesEntity>>>(UiState.Loading)
    val uiStateReceipeEntityList: StateFlow<UiState<List<RecipesEntity>>> = _uiStateReceipeEntityList

    private val _isRecipeSaved = MutableStateFlow<Boolean>(false)
    val isRecipeSaved: StateFlow<Boolean> get() = _isRecipeSaved
    fun checkSaveFood(id: Int) {
        viewModelScope.launch {
            val isSaved = useCase.isRecipeSaved(id)
            _isRecipeSaved.value = isSaved
        }
    }




    fun getReceipesList() = viewModelScope.launch {
        useCase.getRecipes(context).collect {
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

    fun getReceipeDetail(id: Int?) = viewModelScope.launch(Dispatchers.IO) {
        useCase.getRecipeDetail(context, id).collect {
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

    //    fun saveCart() {
//        viewModelScope.launch {
//            val currentRecipesEntity = (_uiStateReceipeEntity.value as? UiState.Success)?.data
//
//            if (currentRecipesEntity != null) {
//                try {
//                    cartRepository.saveRecipes(currentRecipesEntity)
//                    _uiStateReceipeEntity.value = UiState.Success(currentRecipesEntity)
//                } catch (e: Exception) {
//                    _uiStateReceipeEntity.value = UiState.Error(e.message ?: "Unknown error occurred")
//                }
//            } else {
//                _uiStateReceipeEntity.value = UiState.Error("No RecipesEntity available to save")
//            }
//        }
//    }
    fun saveCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentRecipesEntity = (_uiStateReceipeEntity.value as? UiState.Success)?.data

            if (currentRecipesEntity != null) {
                try {
                    useCase.saveRecipe(currentRecipesEntity)
                    _uiStateReceipeEntity.value = UiState.Success(currentRecipesEntity)
                } catch (e: Exception) {
                    _uiStateReceipeEntity.value = UiState.Error(e.message ?: "Unknown error occurred")
                }
            } else {
                _uiStateReceipeEntity.value = UiState.Error("No RecipesEntity available to save")
            }
        }
    }

    fun getCart() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiStateReceipeEntityList.value = UiState.Loading
            try {
                val recipesEntityList = useCase.getSavedRecipes()

                _uiStateReceipeEntityList.value = UiState.Success(recipesEntityList)
            } catch (e: Exception) {
                _uiStateReceipeEntityList.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun deleteCart() {
        val currentRecipesEntity = (_uiStateReceipeEntity.value as? UiState.Success)?.data
        viewModelScope.launch(Dispatchers.IO) {
            if (currentRecipesEntity != null) {
                try {
                   val result = useCase.deleteRecipe(currentRecipesEntity)
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