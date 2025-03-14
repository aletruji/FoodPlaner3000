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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.magnifier
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
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
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
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavHostController,
               viewModel: ViewModel = viewModel()){

    val items = viewModel.items
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var deletedItem by remember { mutableStateOf<Item?>(null) }
    var deletedItemIndex by remember { mutableStateOf<Int?>(null) }




    Column(modifier = Modifier
        .fillMaxSize()
    ){

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(Color(0xFFFFA500))
                .padding(7.dp)
        ) {
            Text(
                text = "Food Planer 3000",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-20.dp)),
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic


            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF212121),shape = RoundedCornerShape(16.dp))
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Einkaufsliste...               ",

                    color = Color.White
                )


            }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .size(180.dp,80.dp)
                        .align(Alignment.BottomEnd)
                        .padding(top = 6.dp)

                )

        }

        Divider(
            thickness = 15.dp, // Dicke der Linie
            color = Color(0xff8BC34A) // Farbe der Linie
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121))

        ){



            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = viewModel.items,
                    key = { _, item -> item.name } // Verwende `item.name` als eindeutigen Schlüssel
                ) { index, item ->
                    val dismissState = rememberDismissState()
                    var showEditDialog by remember { mutableStateOf(false) }
                    var itemToEdit by remember { mutableStateOf<Item?>(null) }
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    var itemToDelete by remember { mutableStateOf<Item?>(null) }
                    var isHandled by remember { mutableStateOf(false) }
                    val removedItemName = item.name
                    val removedQuantity = item.quantity
                    val removedItemUnit = item.unit



                    if (dismissState.isDismissed(DismissDirection.EndToStart)&& !isHandled) {
                        isHandled = true
                        val removedItem = item
                        // Entferne das Item direkt aus der Liste
                        viewModel.removeItem(index)

                        println("schrit 1")

                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "deleted ${removedItem.name}",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short

                            )
                            println("schritt0")
                            if (result == SnackbarResult.ActionPerformed) {
                                println("schritt1.5")
                                // Stelle das gelöschte Item wieder her
                                viewModel.addItem(removedItemName,removedQuantity, removedItemUnit)
                                println("additem")
                                isHandled = false
                                dismissState.reset()
                            }
                            dismissState.reset()
                        }


                        // Setze den Zustand des Swipe zurück



                    }
                    if (showEditDialog == true) {
                        EditItemDialog(
                            item = itemToEdit!!,
                            onDismiss = { showEditDialog = false },
                            onConfirm = { newName, newQuantity, newUnit ->
                                viewModel.onEditItem(itemToEdit!!, newName, newQuantity, newUnit )
                                showEditDialog = false
                            }
                        )
                    }
                    if (showDeleteDialog && itemToDelete != null) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Confirm Delete") },
                            text = { Text("Do you want to delete ${itemToDelete!!.name}?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.removeItem(items.indexOf(itemToDelete!!)) // Lösche das Item
                                    showDeleteDialog = false // Schließe den Dialog

                                }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(
                            DismissDirection.EndToStart, // Nach links ziehen für "Delete"
                            DismissDirection.StartToEnd  // Nach rechts ziehen für "Edit"
                        ),
                        background = {
                            val direction = dismissState.dismissDirection
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xFF212121)) // Einheitlicher Hintergrund
                                    .padding(16.dp)
                            ) {
                                when (direction) {
                                    DismissDirection.StartToEnd -> { // Nach-Rechts-Swipen (Edit)
                                        Text(
                                            text = "Edit",
                                            color = Color(0xFF4CAF50), // Grün
                                            modifier = Modifier.align(Alignment.CenterStart) // Links platzieren
                                        )
                                    }
                                    DismissDirection.EndToStart -> { // Nach-Links-Swipen (Delete)
                                        Text(
                                            text = "Delete",
                                            color = Color(0xFFFF0000), // Rot
                                            modifier = Modifier.align(Alignment.CenterEnd) // Rechts platzieren
                                        )
                                    }
                                    else -> Unit // Kein Text
                                }}
                            },

                            dismissContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(
                                            Color(0xff565a47),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = " ${item.quantity}${item.unit} ${item.name}",
                                            fontSize = 18.sp
                                        )
                                        Row(

                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ){
                                        IconButton(onClick = {
                                            itemToEdit = item
                                            showEditDialog = true},
                                            modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Filled.Edit, contentDescription = "Edit")

                                        }
                                            Spacer(modifier = Modifier.width(16.dp))

                                            IconButton(onClick = {
                                                itemToDelete = item
                                               showDeleteDialog = true},
                                                modifier = Modifier.size(24.dp)) {
                                                Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                            }
                                        }
                                    }
                                }
                            }
                    )


                    if (dismissState.progress > 0.5f && dismissState.targetValue == DismissValue.DismissedToEnd) {
                        itemToEdit = item
                        showEditDialog = true
                        scope.launch {
                            dismissState.snapTo(DismissValue.Default) // Edited line: Reset swipe state
                        }
                    }




                }
            }

            var showAddDialog by remember { mutableStateOf(false) }


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
                        viewModel.addItem(name, quantity, unit) // Item hinzufügen
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

            BottomIconButtons(navController = navController)
        }
    }

}

//################################### AddItem #########################################

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

//################################### Edit #########################################
@Composable
fun EditItemDialog(
    item: Item,
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

//################################### BottomIconButtons #########################################


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
        IconButton(onClick = { /* Aktion für den dritten Button */ }) {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color.White)
        }
        // Vierter Button
        IconButton(onClick = { /* Aktion für den vierten Button */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
        }
    }
}

//################################### Prewiev #########################################

@Preview(showBackground = true)
@Composable
fun showPrewiev() {
    val navController = rememberNavController()
    ListScreen(navController = navController)
}