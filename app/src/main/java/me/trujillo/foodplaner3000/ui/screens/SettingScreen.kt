package me.trujillo.foodplaner3000.ui.screens

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.trujillo.foodplaner3000.Viewmodels.DishViewModel
import me.trujillo.foodplaner3000.Viewmodels.DishViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase

@Composable
fun SettingScreen(navController: NavController) {



    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val vm: DishViewModel = viewModel(
        factory = DishViewModelFactory(
            DishRepository(
                db.dishDao(),
                db.categoryDao(),
                db.ingredientDao(),
                db.dishRelationsDao()
            )
        )
    )


    // ---- EXPORT LAUNCHER ----
    val exportLauncher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val json = vm.exportDishesToJson()
                context.contentResolver.openOutputStream(uri)?.use {
                    it.write(json.toByteArray())
                }
            }
        }
    }

    // ---- IMPORT LAUNCHER ----
    val importLauncher = rememberLauncherForActivityResult(
        contract = OpenDocument()
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val json = context.contentResolver.openInputStream(uri)
                    ?.bufferedReader()?.readText()

                if (json != null) {
                    vm.importDishesFromJson(context, json)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
            .padding(16.dp)
    ) {

        Button(onClick = {
            exportLauncher.launch("dishes_export.json")
        }) {
            Text("Gerichte exportieren")
        }

        Button(
            onClick = {
                importLauncher.launch(arrayOf("application/json"))
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Gerichte importieren")
        }
    }
}
