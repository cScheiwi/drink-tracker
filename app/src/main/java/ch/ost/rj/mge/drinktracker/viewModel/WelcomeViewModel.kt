package ch.ost.rj.mge.drinktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ch.ost.rj.mge.drinktracker.dao.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.repositroy.PersonRepositroy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// evt. Saved state module for ViewModels (data survies death of proces, when killed by OS)
class WelcomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PersonRepositroy
    val user: Person?

    init {
        val personDao = DrinkTrackerDatabase.getDatabase(application).personDao()
        repository = PersonRepositroy(personDao)
        user = repository.user
    }

    fun insert(user: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }
}