package me.trujillo.foodplaner3000.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import me.trujillo.foodplaner3000.AddDishDialog
import me.trujillo.foodplaner3000.Viewmodels.DishViewModel
import me.trujillo.foodplaner3000.Viewmodels.DishViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase

@Composable
fun DetailScreen(
    dishId: Int,
    navController: NavHostController

) {


    var showEditDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val dishRepo = DishRepository(
        db.dishDao(),
        db.categoryDao(),
        db.ingredientDao(),
        db.dishRelationsDao()
    )

    val dishViewModel: DishViewModel = viewModel(factory = DishViewModelFactory(dishRepo))

    val categories by dishViewModel.getCategoriesForDish(dishId).collectAsState(initial = emptyList())
    val ingredients by dishViewModel.getIngredientsForDish(dishId).collectAsState(initial = emptyList())

    val dish by dishViewModel.getDishById(dishId).collectAsState(initial = null)

    if (dish == null) {
        Text("Loading...")
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF212121))
            .padding(16.dp)
    ) {

        // Bild
        item {
            dish!!.imagePath?.let { path ->



                    Image(
                        painter = rememberAsyncImagePainter(dish!!.imagePath),
                        contentDescription = dish!!.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )


            }
        }

        // Titel
        item {
            Text(
                text = dish!!.name,
                color = Color.White,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Kategorien
        item {
            if (categories.isNotEmpty()) {
                Text("Categories:", color = Color.Gray)
                Row {
                    categories.forEach {
                        Text(
                            text = it,
                            color = Color.White,
                            modifier = Modifier
                                .padding(end = 8.dp, top = 4.dp)
                                .background(Color(0xFF424242))
                                .padding(6.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }

        // Beschreibung
        item {
            dish!!.description?.let {
                Text("Description:", color = Color.Gray)
                Text(it, color = Color.White)
                Spacer(Modifier.height(12.dp))
            }
        }

        // Zutaten
        item {
            if (ingredients.isNotEmpty()) {
                Text("Ingredients:", color = Color.Gray)
                ingredients.forEach {
                    Text(
                        text = "${it.name}  -  ${it.quantity ?: ""} ${it.unit}",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
            }
        }

        // Anleitung
        item {
            dish!!.instructions?.let {
                Text("Instructions:", color = Color.Gray)
                Text(it, color = Color.White)
                Spacer(Modifier.height(24.dp))
            }
        }

        // Edit Button
        item {
            IconButton(
                onClick = {
                    showEditDialog = true
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
    if (showEditDialog) {
        AddDishDialog(
            initialName = dish!!.name,
            initialDescription = dish!!.description ?: "",
            initialInstructions = dish!!.instructions ?: "",
            initialCategories = categories,
            initialIngredients = ingredients.map { it.name to ((it.quantity ?: 0.0) to it.unit) },
            initialImagePath = dish!!.imagePath,
            onDismiss = { showEditDialog = false },
            onSave = { name, desc, instr, cats, ings, img ->
                dishViewModel.updateDish(
                    updatedDish = dish!!.copy(
                        name = name,
                        description = desc,
                        instructions = instr,
                        imagePath = img
                    ),
                    categories = cats,
                    ingredients = ings
                )
                showEditDialog = false
            }
        )
    }

}
