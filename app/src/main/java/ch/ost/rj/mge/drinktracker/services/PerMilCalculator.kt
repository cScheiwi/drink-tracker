package ch.ost.rj.mge.drinktracker.services

import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.Gender
import ch.ost.rj.mge.drinktracker.entity.Person

// https://www.plakos.de/promillerechner/
class PerMilCalculator {
    companion object {
        private const val MALE_BODY_LIQUID_MULTIPLICATOR = 0.8
        private const val FEMALE_BODY_LIQUID_MULTIPLICATOR = 0.7

        @JvmStatic
        fun calculatePerMil(person: Person, drinks: List<Drink>): Double {
            val bodyLiquid: Double
            val bodyLiquidMultiplicator: Double =
                if (person.gender == Gender.MALE) {
                    MALE_BODY_LIQUID_MULTIPLICATOR
                } else {
                    FEMALE_BODY_LIQUID_MULTIPLICATOR
                }

            bodyLiquid = person.weight * bodyLiquidMultiplicator

            var totalGramsOfAlcohol = 0.0
            drinks.forEach {
                val gramsOfAlcoholDrink: Double = it.quantity *
                        it.quantityUnit.toCentiliterMultiplicator * it.percentByVolume * 0.08
                totalGramsOfAlcohol += gramsOfAlcoholDrink
            }

            return totalGramsOfAlcohol / bodyLiquid
        }
    }
}