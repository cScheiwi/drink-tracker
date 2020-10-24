package ch.ost.rj.mge.drinktracker.dao

import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate

@Dao
interface DrinkTemplateDao {
    @Query("SELECT * FROM drinkTemplate")
    fun findDrinkTemplates(): List<DrinkTemplate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drinkTemplate: DrinkTemplate)

    @Query("DELETE FROM drinkTemplate")
    fun deleteAll()
}