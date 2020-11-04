package ch.ost.rj.mge.drinktracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ch.ost.rj.mge.drinktracker.database.DrinkTrackerDatabase
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.DrinkTemplate
import ch.ost.rj.mge.drinktracker.entity.Person
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit
import ch.ost.rj.mge.drinktracker.repository.DrinkRepository
import ch.ost.rj.mge.drinktracker.repository.DrinkTemplateRepository
import ch.ost.rj.mge.drinktracker.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val personRepository: PersonRepository
    private val drinkRepository: DrinkRepository
    private val drinkTemplateRepository: DrinkTemplateRepository

    // TODO: 04.11.2020 nur noch userLive verwenden 
    val userLive: LiveData<Person>
    val user: Person?

    val drinks: LiveData<List<Drink>>

    private val drinkTemplates: List<DrinkTemplate>

    init {
        val personDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).personDao()
        personRepository = PersonRepository(personDao)
        userLive = personRepository.liveUser
        user = personRepository.user
        
        val drinkDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope).drinkDao()
        drinkRepository = DrinkRepository(drinkDao)
        drinks = drinkRepository.drinks

        val drinkTemplateDao = DrinkTrackerDatabase.getDatabase(application, viewModelScope)
            .drinkTemplateDao()
        drinkTemplateRepository = DrinkTemplateRepository(drinkTemplateDao)
        drinkTemplates = drinkTemplateRepository.drinkTemplates
    }

    fun getQuantityByName(name: String): Double {
        return drinkTemplateRepository.getQuantityByName(name)
    }

    fun getQuantityUnitByName(name: String): QuantityUnit {
        return drinkTemplateRepository.getQuantityUnitByName(name)
    }

    fun getPercentByVolumeByName(name: String): Double {
        return drinkTemplateRepository.getPercentByVolumeByName(name)
    }

    fun getAllDrinkTemplateNames(): List<String> {
        return drinkTemplateRepository.getAllNames()
    }

    fun insertDrink(drink: Drink) = viewModelScope.launch(Dispatchers.IO) {
        drinkRepository.insert(drink)
    }

    fun updateUser(user: Person) {
        personRepository.update(user)
    }

    fun deleteAllDrinks() {
        drinkRepository.deleteAll()
    }
}