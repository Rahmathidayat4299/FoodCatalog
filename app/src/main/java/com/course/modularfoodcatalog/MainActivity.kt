package com.course.modularfoodcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.course.core.utils.Routes
import com.course.modularfoodcatalog.componentcompose.RecipeDetailScreen
import com.course.modularfoodcatalog.componentcompose.RecipesScreen
import com.course.modularfoodcatalog.ui.theme.ModularFoodCatalogTheme
import com.course.modularfoodcatalog.viewmodels.MainViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModularFoodCatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    val mainViewModel: MainViewModel = koinViewModel()
    ModularFoodCatalogTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.LIST_SCREEN) {
            composable(Routes.LIST_SCREEN) {
                RecipesScreen(navigation = navController, mainViewModel)
            }
            composable(
                Routes.DETAIL_SCREEN, arguments = listOf(navArgument("idValue") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                RecipeDetailScreen(
                    navController,
                    mainViewModel,
                    backStackEntry.arguments?.getInt(Routes.Values.IDVALUE, 0)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModularFoodCatalogTheme {

    }
}