package ch.ost.rj.mge.drinktracker.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.Person

@Dao
interface DrinkTemplateDao {
    @Query("SELECT * FROM drinkTemplate")
    fun findDrinkTemplates(): List<DrinkTemplate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drinkTemplate: DrinkTemplate)
}