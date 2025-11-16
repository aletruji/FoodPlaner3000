package me.trujillo.foodplaner3000


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.trujillo.foodplaner3000.ui.BottomIconButtons
import me.trujillo.foodplaner3000.ui.TopSection
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainApp(snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "screen1"
    Scaffold(
        topBar = {
Column{
    val title = when (currentRoute) {
        "screen1" -> "Einkaufsliste..."
        "screen2" -> "Gerichteliste..."
        "screen3" -> "Randomizer..."
        else -> "Food Planer 3000"}
            TopSection(snackbarHostState, title)
    HorizontalDivider(
                thickness = 15.dp,
                color = Color(0xff8BC34A)
            )
        }},
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFFFFA500))
            ) {
                BottomIconButtons(navController)
            }
        }

    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "screen1",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("screen1") { ListScreen(navController) }
            composable("screen2") { GerichteScreen(navController) }
            // composable("screen3") { RandomPlan(navController) }
        }
    }
}

