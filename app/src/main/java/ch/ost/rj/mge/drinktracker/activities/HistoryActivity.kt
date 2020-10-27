package ch.ost.rj.mge.drinktracker.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.ost.rj.mge.drinktracker.services.AlcoholReducerAlarmReceiver
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.adapter.DrinkListAdapter
import ch.ost.rj.mge.drinktracker.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val AMOUNT_OF_REDUCES_PER_HOUR = 60
    }

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
            drinks?.let { adapter.setDrinks(it) }
        })

        historyViewModel.userLive.observe(this, Observer {
            val alcoholLevel = findViewById<TextView>(R.id.history_alcohol_level_text)
            val perMil = "%.2f".format(it.perMil).toDouble()
            alcoholLevel.text = perMil.toString()
            // TODO 1 (optional): only start alcohol reducer if alcohol level is higher than zero
/*            if (it.perMil > 0) {
                scheduleAlcoholReducer()
            }*/
        })

        createButton = findViewById(R.id.history_create)
        createButton?.setOnClickListener { showCreateDialog() }

        deleteAllButton = findViewById(R.id.history_reset_data)
        deleteAllButton?.setOnClickListener { deleteAllDrinks() }

        scheduleAlcoholReducer()
    }

    private fun deleteAllDrinks() {
        historyViewModel.deleteAllDrinks()
        val user: ch.ost.rj.mge.drinktracker.entity.Person = historyViewModel.user!!
        user.perMil = 0.0
        historyViewModel.updateUser(user)
    }

    private fun scheduleAlcoholReducer() {
        val intent = Intent(applicationContext, AlcoholReducerAlarmReceiver::class.java)

        // TODO 1 (optional): implement reactivation properly
/*        if (PendingIntent.getBroadcast(
                applicationContext,
                AlcoholReducerAlarmReceiver.REQUEST_CODE,
                intent,
                PendingIntent.FLAG_NO_CREATE
            ) == null
        ) {*/
        val pIntent = PendingIntent.getBroadcast(
            this,
            AlcoholReducerAlarmReceiver.REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            (60 / AMOUNT_OF_REDUCES_PER_HOUR * 60 * 1000).toLong(),
            pIntent
        )
//        }
    }

    private fun showCreateDialog() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
}