package ch.ost.rj.mge.drinktracker.repositroy

import androidx.lifecycle.LiveData
import ch.ost.rj.mge.drinktracker.dao.DrinkTemplateDao
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate

class DrinkTemplateRepository (private val drinkTemplateDao: DrinkTemplateDao) {
    val drinkTemplates: List<DrinkTemplate> = drinkTemplateDao.findDrinkTemplates()
}