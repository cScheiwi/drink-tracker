package ch.ost.rj.mge.drinktracker.dao

import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit

@Dao
interface DrinkTemplateDao {
    @Query("SELECT * FROM drinkTemplate")
    fun findDrinkTemplates(): List<DrinkTemplate>

    @Query("SELECT name FROM drinkTemplate")
    fun findDrinkTemplateNames(): List<String>

    @Query("SELECT quantity FROM drinkTemplate WHERE name = :name")
    fun getQuantityByName(name: String): Double

    @Query("SELECT quantityUnit FROM drinkTemplate WHERE name = :name")
    fun getQuantityUnitByName(name: String): QuantityUnit

    @Query("SELECT percentByVolume FROM drinkTemplate WHERE name = :name")
    fun getPercentByVolumeByName(name: String): Double

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drinkTemplate: DrinkTemplate)

    @Query("DELETE FROM drinkTemplate")
    fun deleteAll()
}