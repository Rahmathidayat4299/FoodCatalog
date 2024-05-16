package com.course.modularfoodcatalog.componentcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.course.modularfoodcatalog.R


/**
 *hrahm,26/04/2024, 05:31
 **/


@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
            ),
            BottomBarItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Default.Favorite
            ),
            BottomBarItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle
            ),
        )
        navigationItems.map {
            NavigationBarItem(modifier = Modifier.padding(0.dp),
                icon = {
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp) // Adjust padding values as needed
                    ) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = it.title
                        )
                    }
                },
                label = {
                    Text(
                        it.title,
                    )
                },
                selected = it.title == navigationItems[0].title,
                onClick = {}
            )
        }
    }
}


data class BottomBarItem(val title: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun Preview() {

    BottomBar()

}