package me.trujillo.foodplaner3000.data.enums

enum class Unit (val conversionFactorToBase: Int) {
    g(1), kg(1000),
    l(1000), ml(1),
    EL(15), TL(5),
    x( 1)
}
