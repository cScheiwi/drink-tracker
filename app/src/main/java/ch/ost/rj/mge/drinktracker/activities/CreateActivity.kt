package ch.ost.rj.mge.drinktracker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit
import ch.ost.rj.mge.drinktracker.services.InputVerificationService
import ch.ost.rj.mge.drinktracker.viewModel.HistoryViewModel
import java.util.Calendar

class CreateActivity : AppCompatActivity() {

    companion object {
        private const val FULL_VISIBLE_ALPHA = 1.0f
        private const val HALF_VISIBLE_ALPHA = 0.5f
    }

    private var drinksSpinner: Spinner? = null
    private var quantityEditText: EditText? = null
    private var quantityUnitTextView: TextView? = null
    private var quantityUnit: QuantityUnit? = null
    private var confirmButton: View? = null

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        quantityEditText = findViewById(R.id.create_input_amount)
        quantityEditText?.setText("3.3")
        quantityEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateCreateButton()
            }
        })

        // TODO load this data from database with CursorAdapter, rather than static from resources
        // https://developer.android.com/guide/topics/ui/controls/spinner
        drinksSpinner = findViewById(R.id.create_drinks_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.drinks_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            drinksSpinner?.adapter = adapter
        }

        quantityUnitTextView = findViewById(R.id.create_text_unit)
        quantityUnit = quantityUnitTextView?.text?.toString()?.let { QuantityUnit.fromShortName(it) }

        confirmButton = findViewById(R.id.create_confirm_button)
        confirmButton?.setOnClickListener { showHistoryActivity() }

        updateCreateButton()
    }

    private fun updateCreateButton() {
        val amountEditTextIsValid: Boolean =
            InputVerificationService.hasValidNumberInput(quantityEditText)
        val buttonAlpha: Float = if (amountEditTextIsValid) FULL_VISIBLE_ALPHA else HALF_VISIBLE_ALPHA
        confirmButton?.isEnabled = amountEditTextIsValid
        confirmButton?.alpha = buttonAlpha
    }

    private fun showHistoryActivity() {

        val quantity: Double = quantityEditText?.text.toString().toDouble()
        // TODO set data according to selected drinkTemplate (default beer)
        val drink = Drink(
            "Test", // here
            quantity,
            quantityUnit!!,
            20.0, // here
            Calendar.getInstance().time)
        historyViewModel.insertDrink(drink)

        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}