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
import ch.ost.rj.mge.drinktracker.viewModel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private var createButton: View? = null
    private var deleteAllButton: View? = null
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
        })

        historyViewModel.userLive.observe(this, Observer {
            val alcoholLevel = findViewById<TextView>(R.id.history_alcohol_level_text)
            val perMil = "%.2f".format(it.perMil).toDouble()
            alcoholLevel.text = perMil.toString()
        })

        createButton = findViewById(R.id.history_create)
        createButton?.setOnClickListener { showCreateDialog() }

        deleteAllButton = findViewById(R.id.history_reset_data)
        deleteAllButton?.setOnClickListener{deleteAllDrinks()}
    }

    private fun deleteAllDrinks() {
        historyViewModel.deleteAllDrinks()
        val user: ch.ost.rj.mge.drinktracker.entity.Person = historyViewModel.user!!
        user.perMil = 0.0
        historyViewModel.updateUser(user)
    }

    private fun showCreateDialog() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
}