package me.trujillo.foodplaner3000.data.enums

enum class UnitType {
    WEIGHT,
    VOLUME,
    PIECE
}

enum class Unit1(
    val type: UnitType,
    val toBase: (Double) -> Double,
    val fromBase: (Double) -> Double
) {

    g(
        type = UnitType.WEIGHT,
        toBase = { it },
        fromBase = { it }
    ),

    kg(
        type = UnitType.WEIGHT,
        toBase = { it * 1000 },
        fromBase = { it / 1000 }
    ),

    ml(
        type = UnitType.VOLUME,
        toBase = { it },
        fromBase = { it }
    ),

    l(
        type = UnitType.VOLUME,
        toBase = { it * 1000 },
        fromBase = { it / 1000 }
    ),

    TL(
        type = UnitType.VOLUME,
        toBase = { it * 5 },    // 1 TL = ca. 5 ml
        fromBase = { it / 5 }
    ),

    EL(
        type = UnitType.VOLUME,
        toBase = { it * 15 },   // 1 EL = ca. 15 ml
        fromBase = { it / 15 }
    ),

    x(
        type = UnitType.PIECE,
        toBase = { it },
        fromBase = { it }
    );
}
