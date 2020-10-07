package ch.ost.rj.mge.drinktracker.services

import android.widget.EditText

class InputVerificationService {
    companion object {
        fun hasValidInput(editText: EditText?): Boolean {
            return editText?.text?.isNotEmpty()!! && editText.error == null
        }
    }
}