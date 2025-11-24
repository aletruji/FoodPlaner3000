package me.trujillo.foodplaner3000

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import me.trujillo.foodplaner3000.data.enums.Unit1
import me.trujillo.foodplaner3000.ui.UnitDropdown
import java.io.File


@Composable
fun AddDishDialog(
    initialName: String = "",
    initialDescription: String = "",
    initialInstructions: String = "",
    initialCategories: List<String> = emptyList(),
    initialIngredients: List<Pair<String, Pair<Double, Unit1>>> = emptyList(),
    initialImagePath: String? = null,
    onDismiss: () -> Unit,
    onSave: (
        String,                 // name
        String?,                // description
        String?,                // instructions
        List<String>,           // categories
        List<Pair<String, Pair<Double, Unit1>>>,
        String? //bildpfad
    ) -> Unit
) {


// URI für Kamera-Foto
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    var cameraFile by remember { mutableStateOf<File?>(null) }
    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }
    var instructions by remember { mutableStateOf(initialInstructions) }

    var categories by remember { mutableStateOf(initialCategories) }
    var ingredients by remember { mutableStateOf(initialIngredients) }

    var imagePath by remember { mutableStateOf(initialImagePath) }
    // Kategorien

    var newCategory by remember { mutableStateOf("") }

    // Zutaten

    var newIngredientName by remember { mutableStateOf("") }
    var newIngredientQuantity by remember { mutableStateOf("") }
    var newIngredientUnit by remember { mutableStateOf(Unit1.g) }

    val context = LocalContext.current

// --- Launcher für Galerie ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imagePath = uri.toString()
        }
    }

// --- Launcher für Kamera ---
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && cameraFile != null) {
            imagePath = cameraFile!!.absolutePath
        }
    }




    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Neues Gericht") },
        text = {
            Column( modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)         // maximale Dialoghöhe
                .verticalScroll(rememberScrollState())) {


                // =============================================
//      BILD ABSCHNITT (Option B)
// =============================================
                Text("Foto:", fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(8.dp))

// Bild-Vorschau (falls vorhanden)
                if (imagePath != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imagePath),
                        contentDescription = "Dish image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Kein Bild ausgewählt", color = Color.White)
                    }
                }

                Spacer(Modifier.height(8.dp))

// Button öffnet ein Menü (Kamera oder Galerie)
                var showMenu by remember { mutableStateOf(false) }

                Box {
                    androidx.compose.material3.Button(onClick = { showMenu = true }) {
                        Text("Foto hinzufügen")
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Aus Galerie wählen") },
                            onClick = {
                                galleryLauncher.launch("image/*")
                                showMenu = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Foto aufnehmen") },
                            onClick = {
                                cameraFile = File(context.filesDir, "dish_temp.jpg")

                                val uri = FileProvider.getUriForFile(
                                    context,
                                    context.packageName + ".provider",
                                    cameraFile!!
                                )

                                cameraLauncher.launch(uri)
                                showMenu = false
                            }
                        )

                    }
                }

                Spacer(Modifier.height(20.dp))

                // -------------------------------
                // NAME
                // -------------------------------
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // -------------------------------
                // DESCRIPTION
                // -------------------------------
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Beschreibung") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                // -------------------------------
                // INSTRUCTIONS
                // -------------------------------
                OutlinedTextField(
                    value = instructions,
                    onValueChange = { instructions = it },
                    label = { Text("Anleitung") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))


                // ===================================================
                //                KATEGORIEN
                // ===================================================
                Text("Kategorien:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                categories.forEach { cat ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("• $cat", color = Color.DarkGray)
                        Spacer(Modifier.width(8.dp))
                        IconButton(onClick = {
                            categories = categories - cat
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "delete", tint = Color.Red)
                        }
                    }
                }


                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Neue Kategorie") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(8.dp))

                    androidx.compose.material3.Button(
                        onClick = {
                            if (newCategory.isNotBlank()) {
                                categories = categories + newCategory.trim()
                                newCategory = ""
                            }
                        }
                    ) {
                        Text("Add")
                    }
                }

                Spacer(Modifier.height(20.dp))


                // ===================================================
                //                ZUTATEN
                // ===================================================
                Text("Zutaten:", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

                ingredients.forEach { ing ->
                    val (iname, qtyUnit) = ing
                    val (qty, unit) = qtyUnit

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("• $iname ($qty ${unit.name})", color = Color.DarkGray)
                        Spacer(Modifier.width(8.dp))

                        IconButton(onClick = {
                            ingredients = ingredients - ing
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "delete ingredient", tint = Color.Red)
                        }
                    }
                }


                Spacer(Modifier.height(10.dp))

                // Eingabefelder für neue Zutat
                OutlinedTextField(
                    value = newIngredientName,
                    onValueChange = { newIngredientName = it },
                    label = { Text("Zutat") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newIngredientQuantity,
                        onValueChange = { newIngredientQuantity = it },
                        label = { Text("Menge") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.width(8.dp))

                    // Dropdown für Unit
                    UnitDropdown(
                        selectedUnit = newIngredientUnit,
                        onSelect = { newIngredientUnit = it }
                    )
                }

                Spacer(Modifier.height(8.dp))

                androidx.compose.material3.Button(
                    onClick = {
                        val qty = newIngredientQuantity.toDoubleOrNull()
                        if (qty != null && newIngredientName.isNotBlank()) {
                            ingredients = ingredients + (
                                    newIngredientName to (qty to newIngredientUnit)
                                    )

                            newIngredientName = ""
                            newIngredientQuantity = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Zutat hinzufügen")
                }
            }
        },
        confirmButton = {
            Text(
                "Speichern",
                modifier = Modifier
                    .clickable {
                        if (name.isNotBlank()) {
                            onSave(
                                name,
                                description,
                                instructions,
                                categories,
                                ingredients,
                                imagePath
                            )
                        }
                        onDismiss()
                    }
                    .padding(8.dp)
            )
        },
        dismissButton = {
            Text(
                "Abbrechen",
                modifier = Modifier
                    .clickable { onDismiss() }
                    .padding(8.dp)
            )
        }
    )
}