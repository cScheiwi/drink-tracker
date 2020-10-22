package ch.ost.rj.mge.drinktracker.repositroy

import androidx.lifecycle.LiveData
import ch.ost.rj.mge.drinktracker.dao.PersonDao
import ch.ost.rj.mge.drinktracker.entity.Person

class PersonRepository (private val personDao: PersonDao) {
    val user : Person? = personDao.findPerson()

    fun insert(user : Person) {
        personDao.insert(user)
    }

    fun delete(user: Person) {
        personDao.delete(user)
    }
}