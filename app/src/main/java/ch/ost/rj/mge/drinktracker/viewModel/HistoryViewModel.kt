package ch.ost.rj.mge.drinktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ch.ost.rj.mge.drinktracker.dao.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.repositroy.DrinkRepository
import ch.ost.rj.mge.drinktracker.repositroy.PersonRepository

// ViewModel kann evt. auch für Create verwendet werden
class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val personRepository: PersonRepository
    private val drinkRepository: DrinkRepository
    // wird für notification gebraucht
    val user: Person?
    // drinks sollten automatisch neu geladen werden, wenn in DB hinzugefügt
    val drinks: LiveData<List<Drink>>

    init {
        val personDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).personDao()
        personRepository = PersonRepository(personDao)
        user = personRepository.user

        val drinkDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).drinkDao()
        drinkRepository = DrinkRepository(drinkDao)
        drinks = drinkRepository.drinks
    }
}