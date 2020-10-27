package ch.ost.rj.mge.drinktracker.repository

import ch.ost.rj.mge.drinktracker.dao.DrinkTemplateDao
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit

class DrinkTemplateRepository(private val drinkTemplateDao: DrinkTemplateDao) {
    val drinkTemplates: List<DrinkTemplate> = drinkTemplateDao.findDrinkTemplates()

    fun getAllNames(): List<String> {
        return drinkTemplateDao.findDrinkTemplateNames()
    }

    fun getQuantityByName(name: String): Double {
        return drinkTemplateDao.getQuantityByName(name)
    }

    fun getQuantityUnitByName(name: String): QuantityUnit {
        return drinkTemplateDao.getQuantityUnitByName(name)
    }

    fun getPercentByVolumeByName(name: String): Double {
        return drinkTemplateDao.getPercentByVolumeByName(name)
    }
}