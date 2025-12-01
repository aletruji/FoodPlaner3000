package me.trujillo.foodplaner3000.Viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.entities.Dish
import me.trujillo.foodplaner3000.data.enums.Unit1
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.trujillo.foodplaner3000.ExportFile
import me.trujillo.foodplaner3000.data.db.entities.ExportDish
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.min
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers


class DishViewModel(
    private val repo: DishRepository
) : ViewModel() {

    val query = MutableStateFlow("")
    val filterType = MutableStateFlow("Dish")

    // Rohdaten aus DB
    val dishes = repo.allDishes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val ingredientsByDish = repo.getAllIngredientsForAllDishes()
        .map { list -> list.groupBy { it.dishId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    val categoriesByDish = repo.getAllCategoriesForAllDishes()
        .map { list -> list.groupBy { it.dishId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyMap())

    // Fertig gefilterte Liste
    val filteredDishes =
        combine(
            dishes,
            ingredientsByDish,
            categoriesByDish,
            query,
            filterType
        ) { dishes, ingredients, categories, text, filter ->

            when (filter) {
                "Dish" -> dishes.filter {
                    it.name.contains(text, ignoreCase = true)
                }

                "Ingredient" -> dishes.filter { dish ->
                    ingredients[dish.id]
                        ?.any { it.name.contains(text, ignoreCase = true) } == true
                }

                "Category" -> dishes.filter { dish ->
                    categories[dish.id]
                        ?.any { it.categoryName.contains(text, ignoreCase = true) } == true
                }

                else -> dishes
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    // --------------------------------------
    // LOAD FUNCTIONS
    // --------------------------------------
    fun getCategoriesForDish(dishId: Int) =
        repo.getCategoriesForDishFlow(dishId)



    fun getIngredientsForDish(dishId: Int) =
        repo.getIngredientsForDishFlow(dishId)


    // --------------------------------------
    // UPDATE DISH
    // --------------------------------------
    fun updateDish(
        updatedDish: Dish,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>
    ) {
        viewModelScope.launch {
            repo.updateDish(updatedDish, categories, ingredients)
        }
    }

    fun addDish(
        name: String,
        description: String?,
        instructions: String?,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>,
        imagePath: String?
    ) {
        viewModelScope.launch {
            repo.createDish(
                name = name,
                description = description,
                instructions = instructions,
                categories = categories,
                ingredients = ingredients,
                imagePath = imagePath
            )


        }
    }





    fun getRandomDishes(list: List<Dish>,count: Int): List<Dish> {

        println(list.shuffled().take(count))
        return list.shuffled().take(count)

    }



    fun deleteDish(dish: Dish  ){
        viewModelScope.launch {
            repo.deleteDish(dish)
        }
    }

    fun getDishById(id: Int) =
        repo.getDishById(id)




    fun addRandomDishesToShoppingList(
        shoppingVM: ShoppingViewModel,
        dishes: List<Dish>
    ) {
        viewModelScope.launch {
            dishes.forEach { dish ->
                val ingredients = getIngredientsForDish(dish.id).first()
                shoppingVM.addIngredients(ingredients)
            }
        }
    }
    private fun resizeImage(path: String, maxSize: Int = 600): ByteArray? {
        val file = File(path)
        if (!file.exists()) return null

        val original = BitmapFactory.decodeFile(path) ?: return null

        val ratio = min(maxSize / original.width.toFloat(), maxSize / original.height.toFloat())
        val width = (original.width * ratio).toInt()
        val height = (original.height * ratio).toInt()

        val resized = Bitmap.createScaledBitmap(original, width, height, true)

        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.toByteArray()
    }
    private fun encodeImageToBase64(path: String?): String? {
        if (path == null) return null

        val file = File(path)
        if (!file.exists()) return null

        val original = BitmapFactory.decodeFile(path) ?: return null

        val maxSize = 600
        val ratio = min(
            maxSize.toFloat() / original.width,
            maxSize.toFloat() / original.height
        )
        val width = (original.width * ratio).toInt()
        val height = (original.height * ratio).toInt()

        val resized = Bitmap.createScaledBitmap(original, width, height, true)

        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 80, stream)

        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }
    private fun saveBase64Image(context: Context, base64: String): String {
        val bytes = Base64.decode(base64, Base64.DEFAULT)
        val file = File(context.filesDir, "dish_${System.currentTimeMillis()}.jpg")
        file.writeBytes(bytes)
        return file.absolutePath
    }
    suspend fun exportDishesToJson(): String {
        val data = repo.exportAllDishes()

        val exportWithImages = data.dishes.map { d ->
            ExportDish(
                name = d.name,
                description = d.description,
                instructions = d.instructions,
                categories = d.categories,
                ingredients = d.ingredients,
                imageBase64 = encodeImageToBase64(d.imageBase64) // d.imageBase64 enthält jetzt Pfad!
            )
        }

        return Gson().toJson(ExportFile(exportWithImages))
    }

    fun importDishesFromJson(context: Context, json: String) {
        viewModelScope.launch(Dispatchers.IO) {

        val data = Gson().fromJson(json, ExportFile::class.java)

            val converted = data.dishes.map { d ->
                val imagePath = d.imageBase64?.let { saveBase64Image(context, it) }

                ExportDish(
                    name = d.name,
                    description = d.description,
                    instructions = d.instructions,
                    categories = d.categories,
                    ingredients = d.ingredients,
                    imageBase64 = imagePath
                )
            }

            repo.importDishes(ExportFile(converted))
        }
    }




}



