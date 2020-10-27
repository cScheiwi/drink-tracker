package ch.ost.rj.mge.drinktracker.entity

enum class QuantityUnit(val toCentiliterMultiplicator: Double, val shortName: String) {
    LITER(100.0, "l"),
    DECILITER(10.0, "dl"),
    CENTILITER(1.0, "cl"),
    MILLILITER(0.1, "ml");
}