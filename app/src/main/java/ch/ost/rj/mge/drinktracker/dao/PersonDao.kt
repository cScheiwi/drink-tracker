package ch.ost.rj.mge.drinktracker.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.ost.rj.mge.drinktracker.entity.Person

@Dao
interface PersonDao {
    @Query("SELECT * FROM person LIMIT 1")
    fun findPerson(): Person?

    @Query("SELECT * FROM person LIMIT 1")
    fun findPersonLiveData(): LiveData<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Update
    fun update(person: Person)

    @Delete
    fun delete(person: Person?)
}