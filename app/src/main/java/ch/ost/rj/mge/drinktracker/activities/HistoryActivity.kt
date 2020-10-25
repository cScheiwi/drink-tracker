package ch.ost.rj.mge.drinktracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.adapter.DrinkListAdapter
import ch.ost.rj.mge.drinktracker.services.PerMilCalculator
import ch.ost.rj.mge.drinktracker.viewModel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private var createButton: View? = null
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        val drinksRecyclerView = findViewById<RecyclerView>(R.id.history_drink_list)
        val adapter = DrinkListAdapter(this)
        drinksRecyclerView.adapter = adapter
        drinksRecyclerView.layoutManager = LinearLayoutManager(this)

        historyViewModel.drinks.observe(this, Observer { drinks ->
            drinks?.let {adapter.setDrinks(it)}
            updatePerMil()
        })

        createButton = findViewById(R.id.history_create)
        createButton?.setOnClickListener { showCreateDialog() }
    }

    private fun updatePerMil() {
        val alcoholLevel = findViewById<TextView>(R.id.history_alcohol_level_text)
        val perMil = PerMilCalculator.calculatePerMil(historyViewModel.user!!, historyViewModel.drinks.value!!)
        alcoholLevel.text = perMil.toString()
    }

    private fun showCreateDialog() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
}