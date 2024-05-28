package com.course.modularfoodcatalog.componentcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.course.core.data.Receipes
import com.course.core.utils.RecipeMapper
import com.course.core.utils.UiState
import com.course.modularfoodcatalog.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailScreen(navController: NavController, mainViewModel: MainViewModel, id: Int?) {
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomToolbarScreen(navController = navController, title = "Detail", true)
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = Unit) {
                getReceipesDetails(mainViewModel, id)
                id?.let { mainViewModel.checkSaveFood(it) }
            }
            val state = mainViewModel.uiStateReceipeDetail.collectAsState()
            when (state.value) {
                is UiState.Success -> {
                    ProgressLoader(isLoading = false)
                    (state.value as UiState.Success<Receipes.Recipe>).data?.let { recipe ->
                        RecipeDetailView(
                            recipe = recipe,
                            snackbarHostState,
                            mainViewModel,
                            coroutineScope
                        )
//                        RecipeDetailView(recipe = recipe) {
//                            val recipesEntity = RecipeMapper.mapToEntity(recipe)
//                            mainViewModel.setCurrentRecipesEntity(recipesEntity)
//                            val result = mainViewModel.saveCart()
//                            if (result != null) {
//                                coroutineScope.launch {
//                                    snackbarHostState.showSnackbar("Resep disimpan!")
//                                }
//                            }
//                        }
                    }
                }

                is UiState.Loading -> {
                    ProgressLoader(isLoading = true)
                }

                is UiState.Error -> {
                    ProgressLoader(isLoading = false)
                    // Handle Error
                }
            }
        }
    }
}


private fun getReceipesDetails(mainViewModel: MainViewModel, id: Int?) {
    mainViewModel.getReceipeDetail(id)
}

@Composable
fun RecipeDetailView(
    recipe: Receipes.Recipe,
    snackbarHostState: SnackbarHostState,
    mainViewModel: MainViewModel,
    coroutineScope: CoroutineScope
) {
    var isRecipeSaved by remember { mutableStateOf(false) }
    val isRecipeSavedState by mainViewModel.isRecipeSaved.collectAsState()

    LaunchedEffect(Unit) {
        recipe.id?.let { mainViewModel.checkSaveFood(it) }
    }
    LaunchedEffect(isRecipeSavedState) {
        isRecipeSaved = isRecipeSavedState
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(recipe.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = recipe.name ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Ingredients:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            recipe.ingredients?.forEach { ingredient ->
                Text(
                    text = "• $ingredient",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Instructions:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            recipe.instructions?.forEachIndexed { index, instruction ->
                Text(
                    text = "${index + 1}. $instruction",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val recipesEntity = RecipeMapper.mapToEntity(recipe)
                mainViewModel.setCurrentRecipesEntity(recipesEntity)
                if (isRecipeSaved) {
                     mainViewModel.deleteCart()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Resep delete!")
                        isRecipeSaved = false
                    }
                } else {
                     mainViewModel.saveCart()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Resep Save!")
                        isRecipeSaved = true
                    }
                }


            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = if (isRecipeSaved) "Delete" else "Save")
        }
    }
}
