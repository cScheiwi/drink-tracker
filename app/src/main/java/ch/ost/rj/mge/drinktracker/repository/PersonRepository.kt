package ch.ost.rj.mge.drinktracker.repository

import androidx.lifecycle.LiveData
import ch.ost.rj.mge.drinktracker.dao.PersonDao
import ch.ost.rj.mge.drinktracker.entity.Person

class PersonRepository (private val personDao: PersonDao) {
    val user : Person? = personDao.findPerson()
    val liveUser : LiveData<Person> = personDao.findPersonLiveData()

    fun insert(user : Person) {
        personDao.insert(user)
    }

    fun update(user: Person) {
        personDao.update(user)
    }

    fun delete(user: Person) {
        personDao.delete(user)
    }
}