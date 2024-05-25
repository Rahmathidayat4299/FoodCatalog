package com.course.favorite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.course.core.data.local.RecipesEntity
import com.course.core.utils.UiState
import com.course.modularfoodcatalog.componentcompose.CustomToolbarScreen
import com.course.modularfoodcatalog.componentcompose.ProgressLoader
import com.course.modularfoodcatalog.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FavoriteScreen()
        }

    }

    @Composable
    fun FavoriteScreen() {
        val mainViewModel: MainViewModel = koinViewModel()
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                CustomToolbarScreen(title = "Cart", navController = navController, isBack = false)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(10.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LaunchedEffect(Unit) {
                    mainViewModel.getCart()
                }

                val state = mainViewModel.uiStateReceipeEntityList.collectAsState()

                when (state.value) {
                    is UiState.Success -> {
                        ProgressLoader(isLoading = false)
                        ProgressLoader(isLoading = false)
                        val cartItems = (state.value as UiState.Success<List<RecipesEntity>>).data
                        if (cartItems?.isNotEmpty() == true) {
                            CartList(cartItems = cartItems, onDeleteItem = { deletedItem ->
                                // Menghapus item dari daftar
                                mainViewModel.deleteCart()
                            })
                        } else {
                            Text(
                                text = "No Data"
                            )
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

//    @Composable
//    fun CartList(cartItems: List<RecipesEntity>) {
//        LazyColumn {
//            items(cartItems) { cartItem ->
//                CartListItem(cartItem)
//            }
//        }
//    }
@Composable
fun CartList(cartItems: List<RecipesEntity>, onDeleteItem: (RecipesEntity) -> Unit) {
    LazyColumn {
        items(cartItems) { cartItem ->
            CartListItem(cartItem, onDeleteClick = { onDeleteItem(cartItem) })
        }
    }
}


    @Composable
    fun CartListItem(cartItem: RecipesEntity,onDeleteClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(10),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(cartItem.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = cartItem.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Prep Time: ${cartItem.prepTimeMinutes} mins",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Cook Time: ${cartItem.cookTimeMinutes} mins",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Servings: ${cartItem.servings}",
                        fontSize = 14.sp
                    )

                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CartListItemPreview() {
        val recipe = RecipesEntity(
            id = 1,
            name = "Spaghetti Carbonara",
            ingredients = "Pasta, eggs, bacon, cheese, pepper",
            instructions = "1. Cook pasta\n2. Fry bacon\n3. Mix eggs, cheese, and pepper\n4. Combine everything",
            prepTimeMinutes = 20,
            cookTimeMinutes = 30,
            servings = 4,
            difficulty = "Easy",
            cuisine = "Italian",
            caloriesPerServing = 500,
            tags = "pasta, Italian, easy",
            userId = 123,
            image = "https://example.com/spaghetti.jpg",
            rating = 4.5,
            reviewCount = 100,
            mealType = "Lunch"
        )
        CartListItem(recipe, onDeleteClick = {})
    }

}
