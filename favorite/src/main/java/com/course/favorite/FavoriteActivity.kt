package com.course.favorite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.course.core.data.local.RecipesEntity
import com.course.core.utils.UiState
import com.course.modularfoodcatalog.componentcompose.CustomToolbarScreen
import com.course.modularfoodcatalog.componentcompose.ProgressLoader
import com.course.modularfoodcatalog.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

class FavoriteActivity : ComponentActivity() {
    lateinit var navController: NavController
    lateinit var mainViewModel: MainViewModel
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
                CustomToolbarScreen(title = "Cart", navController = navController, isBack = true)
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
                            CartList(cartItems)
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

    @Composable
    fun CartList(cartItems: List<RecipesEntity>) {
        LazyColumn {
            items(cartItems) { cartItem ->
                CartListItem(cartItem)
            }
        }
    }

    @Composable
    fun CartListItem(cartItem: RecipesEntity) {
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
            }
        }
    }

}