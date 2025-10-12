package me.trujillo.foodplaner3000.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun BottomIconButtons(navController: NavHostController) {
    // Row für die Icons am unteren Rand der App
    Row(
        modifier = Modifier
            .fillMaxWidth()  // Füllt die gesamte Breite aus
            .padding(16.dp), // Optionales Padding
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        // Erster Button
        IconButton(onClick = { navController.navigate("screen1") }) {
            Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White)
        }
        // Zweiter Button
        IconButton(onClick = { navController.navigate("screen2") }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.White)
        }
        // Dritter Button
        IconButton(onClick = { navController.navigate("screen3") }) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
        }
        // Vierter Button
        IconButton(onClick = { /* Aktion für den vierten Button */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
        }
    }
}
