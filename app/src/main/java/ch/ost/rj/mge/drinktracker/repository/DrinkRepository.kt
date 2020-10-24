package ch.ost.rj.mge.drinktracker.repository

import androidx.lifecycle.LiveData
import ch.ost.rj.mge.drinktracker.dao.DrinkDao
import ch.ost.rj.mge.drinktracker.entity.Drink

class DrinkRepository (private val drinkDao: DrinkDao) {
    val drinks : LiveData<List<Drink>> = drinkDao.findDrinks()

    fun insert(drink: Drink) {
        drinkDao.insert(drink)
    }

    fun delete(drink: Drink) {
        drinkDao.delete(drink)
    }

    fun deleteAll() {
        drinkDao.deleteAll()
    }
}