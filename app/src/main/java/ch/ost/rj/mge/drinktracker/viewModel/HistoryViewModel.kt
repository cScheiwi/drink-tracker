package ch.ost.rj.mge.drinktracker.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ch.ost.rj.mge.drinktracker.dao.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.repository.DrinkRepository
import ch.ost.rj.mge.drinktracker.repository.DrinkTemplateRepository
import ch.ost.rj.mge.drinktracker.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val personRepository: PersonRepository
    private val drinkRepository: DrinkRepository
    private val drinkTemplateRepository: DrinkTemplateRepository

    // wird für notification gebraucht
    val user: Person?

    // drinks sollten automatisch neu geladen werden, wenn in DB hinzugefügt
    val drinks: LiveData<List<Drink>>

    val drinkTemplates: List<DrinkTemplate>

    init {
        val personDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).personDao()
        personRepository = PersonRepository(personDao)
        user = personRepository.user

        val drinkDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).drinkDao()
        drinkRepository = DrinkRepository(drinkDao)
        drinks = drinkRepository.drinks

        val drinkTemplateDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope)
            .drinkTemplateDao()
        drinkTemplateRepository = DrinkTemplateRepository(drinkTemplateDao)
        drinkTemplates = drinkTemplateRepository.drinkTemplates
    }

    fun insertDrink(drink: Drink) = viewModelScope.launch(Dispatchers.IO) {
        drinkRepository.insert(drink)
    }

}