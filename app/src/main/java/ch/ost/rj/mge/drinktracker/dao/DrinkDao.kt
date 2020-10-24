package ch.ost.rj.mge.drinktracker.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.Drink

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drink")
    fun findDrinks(): LiveData<List<Drink>>

    @Query("SELECT * FROM drink WHERE name = :name")
    fun findByName(name: String): Drink

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drink: Drink)

    @Delete
    fun delete(drink: Drink?)

    @Query("DELETE FROM drink")
    fun deleteAll()
}