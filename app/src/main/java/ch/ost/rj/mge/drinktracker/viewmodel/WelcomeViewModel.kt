package ch.ost.rj.mge.drinktracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ch.ost.rj.mge.drinktracker.database.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WelcomeViewModel(application: Application) : AndroidViewModel(application) {
    private val personRepository: PersonRepository
    val user: Person?

    init {
        val personDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).personDao()
        personRepository = PersonRepository(personDao)
        user = personRepository.user
    }

    fun insert(user: Person) = viewModelScope.launch(Dispatchers.IO) {
        personRepository.insert(user)
    }
}