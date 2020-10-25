package ch.ost.rj.mge.drinktracker.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.entity.Drink
import ch.ost.rj.mge.drinktracker.entity.QuantityUnit
import ch.ost.rj.mge.drinktracker.services.InputVerificationService
import ch.ost.rj.mge.drinktracker.viewModel.HistoryViewModel
import java.util.*


class CreateActivity : AppCompatActivity() {

    private var drinksSpinner: Spinner? = null

    private var quantityEditText: EditText? = null
    private var quantityUnitTextView: TextView? = null
    private var quantityUnit: QuantityUnit? = null

    private var confirmButton: View? = null

    private var currentDrinkName: String? = null

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        quantityEditText = findViewById(R.id.create_input_amount)
        quantityEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateCreateButton()
            }
        })

        quantityUnitTextView = findViewById(R.id.create_text_unit)

        drinksSpinner = findViewById(R.id.create_spinner_drinks)
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                historyViewModel.getAllDrinkTemplateNames())
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        drinksSpinner?.adapter = spinnerArrayAdapter


        drinksSpinner?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View,
                                        position: Int, id: Long
            ) {
                updateQuantityAndUnit()
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        confirmButton = findViewById(R.id.create_button_confirm)
        confirmButton?.setOnClickListener { showHistoryActivity() }

        updateQuantityAndUnit()
        updateCreateButton()
    }

    private fun updateQuantityAndUnit() {

        currentDrinkName = drinksSpinner?.selectedItem.toString()
        val currentQuantity: Double = historyViewModel.getQuantityByName(currentDrinkName!!)
        val currentQuantityUnit: QuantityUnit = historyViewModel.getQuantityUnitByName(currentDrinkName!!)

        quantityEditText?.setText(currentQuantity.toString())
        quantityUnitTextView?.text = currentQuantityUnit.shortName

        quantityUnit = currentQuantityUnit
    }

    private fun updateCreateButton() {
        val amountEditTextIsValid: Boolean =
            InputVerificationService.hasValidNumberInput(quantityEditText)
        val buttonAlpha: Float = if (amountEditTextIsValid)
            WelcomeActivity.FULL_VISIBLE_ALPHA else WelcomeActivity.HALF_VISIBLE_ALPHA
        confirmButton?.isEnabled = amountEditTextIsValid
        confirmButton?.alpha = buttonAlpha
    }

    private fun showHistoryActivity() {

        val drink = Drink(
            currentDrinkName.toString(),
            quantityEditText?.text.toString().toDouble(),
            quantityUnit!!,
            historyViewModel.getPercentByVolumeByName(currentDrinkName.toString()), // here
            Calendar.getInstance().time)
        historyViewModel.insertDrink(drink)

        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }
}