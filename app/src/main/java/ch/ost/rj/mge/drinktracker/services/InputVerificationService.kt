package ch.ost.rj.mge.drinktracker.services

import android.widget.EditText
import ch.ost.rj.mge.drinktracker.entity.Gender
import kotlin.text.*

class InputVerificationService {
    companion object {
        fun hasValidInput(editText: EditText?): Boolean {
            return editText?.text?.isNotEmpty()!! && editText.error == null
        }

        fun hasValidNumberInput(editText: EditText?): Boolean {
            return editText?.text?.isNotEmpty()!! && editText.text?.toString()?.toDoubleOrNull()
                ?.let { true } == true && editText.error == null
        }

        fun hasValidInput(gender: Gender?): Boolean {
            return gender != null
        }
    }
}