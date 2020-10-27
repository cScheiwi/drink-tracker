package ch.ost.rj.mge.drinktracker.services

import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.Gender
import ch.ost.rj.mge.drinktracker.entity.Person

// Origin of the formula: https://www.plakos.de/promillerechner/
class PerMilCalculatorService {
    companion object {
        private const val MALE_BODY_LIQUID_MULTIPLICATOR = 0.8
        private const val FEMALE_BODY_LIQUID_MULTIPLICATOR = 0.7
        private const val SAFETY_PER_MIL_MULTIPLICATOR = 1.1

        @JvmStatic
        fun calculatePerMil(person: Person, drink: Drink): Double {
            val bodyLiquid: Double
            val bodyLiquidMultiplicator: Double =
                if (person.gender == Gender.MALE) {
                    MALE_BODY_LIQUID_MULTIPLICATOR
                } else {
                    FEMALE_BODY_LIQUID_MULTIPLICATOR
                }

            bodyLiquid = person.weight * bodyLiquidMultiplicator

            var totalGramsOfAlcohol = 0.0
            val gramsOfAlcoholDrink: Double = drink.quantity *
                    drink.quantityUnit.toCentiliterMultiplicator * drink.percentByVolume * 0.08
            totalGramsOfAlcohol += gramsOfAlcoholDrink

            return (totalGramsOfAlcohol / bodyLiquid) * SAFETY_PER_MIL_MULTIPLICATOR
        }
    }
}