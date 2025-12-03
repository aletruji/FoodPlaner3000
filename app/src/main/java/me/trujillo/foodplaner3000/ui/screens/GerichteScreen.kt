package me.trujillo.foodplaner3000.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo
import me.trujillo.foodplaner3000.Viewmodels.DishViewModel
import me.trujillo.foodplaner3000.Viewmodels.DishViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase
import me.trujillo.foodplaner3000.data.db.entities.Dish
import me.trujillo.foodplaner3000.AddDishDialog
import me.trujillo.foodplaner3000.ui.IconButtonAddDish
import me.trujillo.foodplaner3000.ui.SimpleFilterDropdown



@Composable
fun GerichteScreen(navController: NavHostController){


    var showAddPlateDialog by remember { mutableStateOf(false) }

    var showAddDishDialog by remember { mutableStateOf(false) }
    var dishToEdit by remember { mutableStateOf<Dish?>(null) }










    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val dishRepo = DishRepository(
        db.dishDao(),
        db.categoryDao(),
        db.ingredientDao(),
        db.dishRelationsDao()
    )

    val dishViewModel: DishViewModel = viewModel(
        factory = DishViewModelFactory(dishRepo)
    )
    val text by dishViewModel.query.collectAsState()
    val filter by dishViewModel.filterType.collectAsState()
    val filteredDishes by dishViewModel.filteredDishes.collectAsState()

    LaunchedEffect(Unit) {
        dishViewModel.categoriesByDish.collect { map ->
            println("CATEGORIES BY DISH = $map")
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()

                .background(Color.LightGray)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


                OutlinedTextField(
                    value = text,

                    onValueChange = { dishViewModel.query.value = it },
                    label = { Text("Search", color = Color.Black) },

                    textStyle = TextStyle(color = Color.Black),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White,

                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,

                        cursorColor = Color.Black
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)


                )

                SimpleFilterDropdown(

                    selected = filter,
                    onSelected = { dishViewModel.filterType.value = it },
                    modifier = Modifier
                        .width(120.dp)
                        .padding( start = 6.dp,end = 6.dp, top = 6.dp)
                        .height(55.dp)

                )

            }





        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF212121))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        items = filteredDishes,
                        key = { it.id }
                    ) { item ->
                        Card(
                            colors = CardDefaults.cardColors(Color(0xFF565A47)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(57.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clickable {
                                    navController.navigate("detail/${item.id}")
                                }

                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${item.name} ",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            dishToEdit = item


                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Edit",
                                            tint = Color(0xFF4CAF50)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(
                                        onClick = {
                                            dishViewModel.deleteDish(item)


                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color(0xFFFF5252)
                                        )
                                    }
                                }
                            }
                        }
                    }

                }



                if (dishToEdit != null) {

                    val categoriesFlow = dishViewModel.getCategoriesForDish(dishToEdit!!.id)
                    val ingredientsFlow = dishViewModel.getIngredientsForDish(dishToEdit!!.id)

                    val categories by categoriesFlow.collectAsState(initial = null)
                    val ingredients by ingredientsFlow.collectAsState(initial = null)

                    if (categories != null && ingredients != null) {
                        AddDishDialog(
                            initialName = dishToEdit!!.name,
                            initialDescription = dishToEdit!!.description ?: "",
                            initialInstructions = dishToEdit!!.instructions ?: "",
                            initialCategories = categories!!,
                            initialIngredients = ingredients!!.map {
                                it.name to ((it.quantity ?: 0.0) to it.unit)
                            },
                            initialImagePath = dishToEdit!!.imagePath,
                            onDismiss = { dishToEdit = null },
                            onSave = { name, desc, instr, cats, ings, imagePath ->
                                dishViewModel.updateDish(
                                    dishToEdit!!.copy(
                                        name = name,
                                        description = desc,
                                        instructions = instr,
                                        imagePath = imagePath
                                    ),
                                    cats,
                                    ings

                                )
                                dishToEdit = null
                            }
                        )
                    }

                }


                if (showAddDishDialog) {
                    AddDishDialog(
                        initialName = "",
                        initialDescription = "",
                        initialInstructions = "",
                        initialCategories = emptyList(),
                        initialIngredients = emptyList(),
                        initialImagePath = null,
                        onDismiss = { showAddDishDialog = false },
                        onSave = { name, desc, instr, cats, ings, imagePath ->
                            dishViewModel.addDish(
                                name,
                                desc,
                                instr,
                                cats,
                                ings,
                                imagePath
                            )
                            showAddDishDialog = false
                        }
                    )
                }

                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    IconButtonAddDish(
                        onClick = { showAddDishDialog = true }



                    )
                }
            }


        }


    }
}








@Preview(showBackground = true)
@Composable
fun PreviewNewScreen() {
    val navController = rememberNavController()
    GerichteScreen(navController = navController)

}

