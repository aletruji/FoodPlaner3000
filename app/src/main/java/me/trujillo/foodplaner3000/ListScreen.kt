package me.trujillo.foodplaner3000

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModel
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.ShoppingListRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList
import me.trujillo.foodplaner3000.data.enums.Unit1


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController){


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context) // <- falls du Room nutzt
    val repo = ShoppingListRepository(db.shoppingListDao())

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
                items(items = items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.quantity} ${item.unit.name} ${item.name}",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        IconButton(onClick = { viewModel.removeItem(item) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }



            var showAddDialog by remember { mutableStateOf(false) }

/*
            IconButton(
                onClick = {
                    showAddDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Rechts platzieren
                    .padding(25.dp)
                    .size(30.dp) // Kleinere Größe des Buttons
                    .background(Color(0xff8BC34A), shape = RoundedCornerShape(50)) // Runder Hintergrund
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, // Das "X"-Icon
                    contentDescription = "Add",
                    tint = Color.White                    ,
                    modifier = Modifier.size(16.dp) // Kleinere Größe des Icons
                )
            }
            if (showAddDialog) {
                AddItemDialog(

                    onAddItem = { name, quantity, unit ->
                        val newItem = ShoppingList(
                            name = name,
                            quantity = quantity,
                            unit = unit
                        )
                        viewModel.addItem(newItem)
                        showAddDialog = false // Dialog schließen
                    },
                    onDismiss = {
                        showAddDialog = false // Dialog schließen
                    }
                )
            }

        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color(0xFFFFA500))
        ){

           // BottomIconButtons(navController = navController)
        }*/
    }}

}

//################################### AddItem #########################################
/*
@Composable
fun AddItemDialog(

    onDismiss: () -> Unit,
    onAddItem: (String, Int, Unit1) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) } // Steuert die Sichtbarkeit des Menüs
    var selectedUnit by remember { mutableStateOf(Unit1.x) } // Aktuell ausgewählte Einheit



    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color(0xFF212121),
        title = {
            Text(
            text = "add new item"
            ) },
        text = {
            Column {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = if (quantity == 0) "" else quantity.toString(), // Zeige leeres Feld, wenn 0
                    onValueChange = {

                            quantity = it.toIntOrNull() ?: 1

                    },
                    label = { Text("quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                UnitDropdownMenu(selectedUnit = selectedUnit,
                    onUnitSelected = { selectedUnit = it } )




            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    val finalQuantity = if (quantity > 0) quantity else 1 // Standardwert 1 setzen
                    onAddItem(name, finalQuantity, selectedUnit)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
/*
//################################### Edit #########################################
@Composable
fun EditItemDialog(
    item: ShoppingList,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, Unit1) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var selectedUnit by remember { mutableStateOf(item.unit) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Edit Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
                UnitDropdownMenu(selectedUnit = selectedUnit,
                    onUnitSelected = { selectedUnit = it } )

            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank() && quantity.isNotBlank()) {
                    onConfirm(name, quantity.toInt(), selectedUnit)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

//################################### Dropdown #########################################
/*
@Composable
fun UnitDropdownMenu(
    selectedUnit: Unit1,
    onUnitSelected: (Unit1) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        // Button zum Öffnen des Menüs
        TextButton(onClick = { expanded = true }) {
            Text("Selected Unit: ${selectedUnit.name}")
        }

        // Dropdown-Menü
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Unit1.values().forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.name) }, // Zeigt den Namen der Einheit
                    onClick = {
                        onUnitSelected(unit) // Rückgabe der ausgewählten Einheit
                        expanded = false // Menü schließen
                    }
                )
            }
        }
    }
}
*/


 */
 */
//################################### BottomIconButtons #########################################


//################################### Prewiev #########################################

@Preview(showBackground = true)
@Composable
fun ShowPrewiev() {
    val navController = rememberNavController()
    ListScreen(navController = navController)
}