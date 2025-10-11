import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import me.trujillo.foodplaner3000.BottomIconButtons
import me.trujillo.foodplaner3000.Dish
import me.trujillo.foodplaner3000.Viewmodels.GerichteViewModel

@Composable
fun RandomPlan(navController: NavHostController, viewModel: GerichteViewModel = viewModel()){
    val gerichte = remember { mutableStateListOf<Dish>() }

    var showAddPlateDialog by remember { mutableStateOf(false) }
    var numberInput by remember { mutableStateOf("") }


    class RandomPlanViewModel : ViewModel() {
        // Logik später hier rein
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header oben
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
                    .background(Color(0xFF212121), shape = RoundedCornerShape(16.dp))
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(text = "RandomPlan...", color = Color.White)
            }
        }

        Divider(thickness = 15.dp, color = Color(0xff8BC34A))

        // 🔸 Inhalt: Row + Liste
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF212121))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val result = numberInput.toIntOrNull() ?: 0
                        if (result > 0) {
                            gerichte.clear()
                            gerichte.addAll(viewModel.getRandomDishes(result))
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Randomize")
                }

                OutlinedTextField(
                    value = numberInput,
                    onValueChange = { input -> numberInput = input.filter { it.isDigit() } },
                    label = { Text("Anzahl") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            //  Liste der Gerichte
            Column {
                gerichte.forEach { dish ->
                    Text(
                        text = dish.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }

        // Floating Button etwas höher
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(
                onClick = { /* action */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xff8BC34A), shape = RoundedCornerShape(50))
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Footer unten
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color(0xFFFFA500))
        ) {
            BottomIconButtons(navController = navController)
        }
    }}

