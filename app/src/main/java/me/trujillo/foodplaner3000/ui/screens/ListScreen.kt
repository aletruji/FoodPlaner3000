package me.trujillo.foodplaner3000.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModel
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.ShoppingListRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList
import me.trujillo.foodplaner3000.ui.AddItemDialog
import me.trujillo.foodplaner3000.ui.IconButtonAdd
import me.trujillo.foodplaner3000.ui.components.ShoppingListItem


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController){



    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val repo = ShoppingListRepository(db.shoppingListDao())
    var itemToEdit by remember { mutableStateOf<ShoppingList?>(null) }


    val viewModel: ShoppingViewModel = viewModel(
        factory = ShoppingViewModelFactory(repo)
    )

    val items by viewModel.items.collectAsState(initial = emptyList()) // ← Flow -> State



    Column(modifier = Modifier
        .fillMaxSize()
    ){



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121))

        ){



            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = items, key = { it.id }) { item ->
                    ShoppingListItem(
                        item = item,
                        onEdit = {  itemToEdit = item
                        },
                        onDelete = { viewModel.removeItem(item) }
                    )
                }
            }

            if (itemToEdit != null) {
                AddItemDialog(
                    initialName = itemToEdit!!.name,
                    initialQuantity = itemToEdit!!.quantity,
                    initialUnit = itemToEdit!!.unit,


                    onConfirm = { name, quantity, unit ->
                        val updated = itemToEdit!!.copy(
                            name = name,
                            quantity = quantity,
                            unit = unit
                        )
                        viewModel.updateItem(updated)
                        itemToEdit = null
                    },
                    onDismiss = { itemToEdit = null }
                    )
            }




            IconButtonAdd(viewModel)


    }}

}



@Preview(showBackground = true)
@Composable
fun ShowPrewiev() {
    val navController = rememberNavController()
    ListScreen(navController = navController)
}