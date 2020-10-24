package ch.ost.rj.mge.drinktracker.repository

import ch.ost.rj.mge.drinktracker.dao.DrinkTemplateDao
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate

class DrinkTemplateRepository (private val drinkTemplateDao: DrinkTemplateDao) {
    val drinkTemplates: List<DrinkTemplate> = drinkTemplateDao.findDrinkTemplates()

    fun getAll() {
        drinkTemplateDao.findDrinkTemplates()
    }

    fun deleteAll() {
        drinkTemplateDao.deleteAll()
    }
}