import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.trujillo.foodplaner3000.Viewmodels.DishViewModel
import me.trujillo.foodplaner3000.Viewmodels.DishViewModelFactory
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModel
import me.trujillo.foodplaner3000.Viewmodels.ShoppingViewModelFactory
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.Repositorys.ShoppingListRepository
import me.trujillo.foodplaner3000.data.db.AppDatabase
import me.trujillo.foodplaner3000.data.db.entities.Dish


@Composable
fun RandomPlan(
    navController: NavHostController

) {

    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

    val dishRepo = DishRepository(
        db.dishDao(),
        db.categoryDao(),
        db.ingredientDao(),
        db.dishRelationsDao()
    )

    val dishViewModel: DishViewModel = viewModel(factory = DishViewModelFactory(dishRepo))
    val allDishes by dishViewModel.dishes.collectAsState(initial = emptyList())

    var numberInput by remember { mutableStateOf("") }
    var randomDishes by remember { mutableStateOf<List<Dish>>(emptyList()) }


    val shoppingRepo = ShoppingListRepository(db.shoppingListDao())
    val shoppingViewModel: ShoppingViewModel = viewModel(
        factory = ShoppingViewModelFactory(shoppingRepo)
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {



        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val count = numberInput.toIntOrNull() ?: 0
                        if (count > 0) {
                            randomDishes = dishViewModel.getRandomDishes(allDishes, count)
                        }
                    }
                ) {
                    Text("Randomize")
                }

                Button(
                    onClick = {
                        dishViewModel.addRandomDishesToShoppingList(
                            shoppingViewModel,
                            randomDishes
                        )
                    }
                ) {
                    Text("to list")
                }



                OutlinedTextField(
                    value = numberInput,
                    onValueChange = { numberInput = it.filter(Char::isDigit) },
                    label = { Text("Anzahl") },
                    modifier = Modifier.width(120.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                randomDishes.forEach { dish ->
                    Text(
                        text = dish.name,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(4.dp)


                    )
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRandomScreen() {
    val navController = rememberNavController()
    RandomPlan(navController = navController)

}
