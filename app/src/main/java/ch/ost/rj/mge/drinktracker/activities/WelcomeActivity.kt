package ch.ost.rj.mge.drinktracker.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import ch.ost.rj.mge.drinktracker.R
import ch.ost.rj.mge.drinktracker.services.InputVerificationService

class WelcomeActivity : AppCompatActivity() {

    companion object {
        private const val MIN_WEIGHT = 40
        private const val FULL_VISIBLE_ALPHA = 1.0f
        private const val HALF_VISIBLE_ALPHA = 0.5f
    }

    private var nameEditText: EditText? = null
    private var weightEditText: EditText? = null
    private var maleRadioButton: RadioButton? = null
    private var femaleRadioButton: RadioButton? = null

    private var loginButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        nameEditText = findViewById(R.id.welcome_input_name)
        //TODO implement addTextChangeListener

        weightEditText = findViewById(R.id.welcome_weight_picker)
        //TODO implement addTextChangeListener

        maleRadioButton = findViewById(R.id.welcome_gender_male)
        femaleRadioButton = findViewById(R.id.welcome_gender_female)
        //TODO implement addTextChangeListener - only one gender should be selected!

        loginButton = findViewById(R.id.welcome_confirm_button)
        loginButton?.setOnClickListener { showHistoryActivity() }

        updateLoginButton()

    }

    private fun updateLoginButton() {
        //TODO works?
        val emailHasError: Boolean = InputVerificationService.hasValidInput(nameEditText)
        val passwordHasError: Boolean = InputVerificationService.hasValidInput(weightEditText)
        val buttonIsEnabled = !emailHasError && !passwordHasError
        val buttonAlpha: Float = if (buttonIsEnabled) FULL_VISIBLE_ALPHA else HALF_VISIBLE_ALPHA
        loginButton?.isEnabled = buttonIsEnabled
        loginButton?.alpha = buttonAlpha
    }

    private fun showHistoryActivity() {
        //TODO implement render to HistoryActivity
/*        val name: String = nameEditText?.text.toString()
        val weight: String = weightEditText?.text.toString()
        val intent: Intent = HistoryActivity.createIntent(this, name, weight)
        startActivity(intent)*/
    }
}