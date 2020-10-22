package ch.ost.rj.mge.drinktracker.entity

enum class QuantityUnit(val toCentiliterMultiplicator: Double) {
    LITER(100.0),
    DECILITER(10.0),
    CENTILITER(1.0),
    MILLILITER(0.1)
}