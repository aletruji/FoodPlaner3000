package me.trujillo.foodplaner3000


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController




@Composable
fun MainApp() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "screen1"
    ) {
        composable("screen1") { ListScreen(navController) }
      //  composable("screen2") { GerichteScreen(navController) }
       // composable("screen3") { RandomPlan(navController) }

    }
}