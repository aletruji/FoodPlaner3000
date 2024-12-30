package me.trujillo.foodplaner3000

data class Item(var name: String, var quantity: Int, var unit: Unit, var gperpiece: Int? = null) {
    fun getQuantity(): String {
        return "$quantity $unit"
    }
}
enum class Unit (val conversionFactorToBase: Double) {
    g(1.0), kg(1000.0),
    l(1000.0), ml(1.0),
    tblspn(15.0), teaspn(5.0),
    peace(1.0)
}