package ch.ost.rj.mge.drinktracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import ch.ost.rj.mge.drinktracker.R

class HistoryActivity : AppCompatActivity() {

    private var createButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // TODO load drinks from database and show in list
        // TODO maybe change to RecyclerView first? (View is a little bit hacky now)

        createButton = findViewById(R.id.history_create)
        createButton?.setOnClickListener { showCreateDialog() }
    }

    private fun showCreateDialog() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)

    }
}