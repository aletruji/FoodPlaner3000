package me.trujillo.foodplaner3000

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import me.trujillo.foodplaner3000.Viewmodels.GerichteViewModel
import me.trujillo.foodplaner3000.Viewmodels.ItemViewModel


@Composable
fun GerichteScreen(navController: NavHostController, viewModel: GerichteViewModel = viewModel()){
    val gerichte = remember { mutableStateListOf<Dish>() }

    var showAddPlateDialog by remember { mutableStateOf(false) }

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
                    text = "Gerichte...               ",

                    color = Color.White
                )


            }
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(viewModel.gerichte) { dish ->
                    DishCard(dish)
                }
            }




            IconButton(
                onClick = { showAddPlateDialog = true },
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

        }
        if (showAddPlateDialog) {
            AddPlateDialog(

                onAddItem = { name, items, categories -> // NEU: Gericht ins ViewModel speichern
                    val newDish = Dish(name, items, categories, imagePath = "")
                    viewModel.addDish(newDish) // NEU: Gericht wird über ViewModel hinzugefügt
                    showAddPlateDialog = false
                },
                onDismiss = {
                    showAddPlateDialog = false // Dialog schließen
                }
            )
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
@Composable
fun DishCard(dish: Dish) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Stellt sicher, dass die Karte in der Grid-Zelle voll ist
            .height(150.dp)
            .clickable { /* Aktion für Details */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val bitmap = loadImageFromFile(dish.imagePath)
            if (bitmap != null) {
                androidx.compose.foundation.Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = dish.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Gray)
                )
                Text(
                    text = dish.name,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center, //
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth() //
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = dish.name,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Funktion zum Laden eines Bildes aus einem Dateipfad
fun loadImageFromFile(path: String?): android.graphics.Bitmap? {
    return try {
        path?.let { BitmapFactory.decodeFile(it) }
    } catch (e: Exception) {
        null
    }
}

@Composable
fun AddPlateDialog(
    onDismiss: () -> Unit,
    onAddItem: (String, List<DishItem>, List<Category>) -> Unit // NEU: `onAddItem` ruft `viewModel.addDish()` auf
) {
    var name by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(mutableListOf<DishItem>()) }
    var categories by remember { mutableStateOf(mutableListOf<Category>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(Unit1.x) }
    var newCategory by remember { mutableStateOf("") }
    val itemViewModel: ItemViewModel = viewModel()
    var selectedItem by remember { mutableStateOf<ShoppingItem?>(null) }





    AlertDialog(
        onDismissRequest = {  },
        containerColor = Color(0xFF212121),
        title = { Text(text = "Add new plate") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Zutatenliste hinzufügen

                items.forEachIndexed { index, dishItem ->
                    Text(
                        text = "- ${dishItem.base.name}: ${dishItem.quantity} ${dishItem.unit}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                newItemName = dishItem.base.name
                                newItemQuantity = dishItem.quantity.toString()
                                selectedUnit = dishItem.unit
                                items.removeAt(index)
                            }
                            .padding(4.dp)
                    )
                }



                Row {
                    OutlinedTextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        label = { Text("Ingredient name") },
                        modifier = Modifier.weight(1f).height(56.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = newItemQuantity,
                        onValueChange = { newItemQuantity = it.filter { it.isDigit() } },
                        label = { Text("Qty") },
                        modifier = Modifier.weight(0.5f).height(56.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                }
                Row{
                    Spacer(modifier = Modifier.width(8.dp))
                    UnitDropdownMenu(selectedUnit = selectedUnit, onUnitSelected = { selectedUnit = it })
                }
                Button(onClick = {
                    if (newItemName.isNotBlank() && newItemQuantity.isNotBlank()) {
                        items.add(DishItem(SingleItem(newItemName, defaultUnit = selectedUnit), newItemQuantity.toInt(), selectedUnit))

                        newItemName = ""
                        newItemQuantity = ""
                    }
                }) {
                    Text("Add Ingredient")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Kategorien hinzufügen
                var newCategoryText by remember { mutableStateOf(TextFieldValue("")) }

                Text("Categories:")
                categories.forEachIndexed { index, category ->
                    Text(
                        text = "- ${category.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Setzt den Text ins Eingabefeld und platziert den Cursor am Ende
                                newCategoryText = TextFieldValue(
                                    text = category.name,
                                    selection = TextRange(category.name.length) // Cursor ans Ende setzen
                                )
                                categories.removeAt(index) // Entferne die Kategorie aus der Liste
                            }
                            .padding(4.dp)
                    )
                }
                OutlinedTextField(
                    value = newCategoryText,
                    onValueChange = { newCategoryText = it },
                    label = { Text("New Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    if (newCategoryText.text.isNotBlank()) {
                        categories.add(Category(newCategoryText.text))
                        newCategoryText = TextFieldValue("") // Eingabe nach dem Hinzufügen leeren
                    }
                }) {
                    Text("Add Category")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    onAddItem(name, items, categories)
                    onDismiss()
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



@Preview(showBackground = true)
@Composable
fun PreviewNewScreen() {
    val navController = rememberNavController()
    GerichteScreen(navController = navController)

}