package com.course.modularfoodcatalog.componentcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 *hrahm,23/04/2024, 19:05
 **/
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbarScreen(navController: NavController, title: String, isBack: Boolean) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(text = title, color = Color.Black, fontSize = 18.sp)
        },
        modifier = Modifier.background(Color.White),
        navigationIcon = {
            if (isBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            } else {
                IconButton(
                    onClick = {
                        // Aksi ketika ikon rumah diklik, jika diperlukan
                    }) {
                    Icon(Icons.Filled.Home, "homeIcon")
                }
            }
        }
    )
}

@Preview(showBackground = true, name = "Custom Toolbar Screen Preview")
@Composable
fun PreviewCustomToolbarScreen() {
    val navController = rememberNavController()
    CustomToolbarScreen(navController = navController, title = "Preview Title", isBack = false)
}