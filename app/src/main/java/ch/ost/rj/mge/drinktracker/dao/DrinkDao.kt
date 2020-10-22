package ch.ost.rj.mge.drinktracker.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.Person

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drink")
    fun findDrinks(): LiveData<List<Drink>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Drink)

    @Delete
    fun delete(person: Drink?)

    @Query("DELETE FROM drink")
    fun deleteAll()
}