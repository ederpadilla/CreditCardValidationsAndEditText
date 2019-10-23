package mx.justo.styling.widget.card

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import mx.justo.styling.R

class MonthAndYearTextWatcher(private val editText: EditText) : TextWatcher {

    private var length: Int = ZERO

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        editText.error = null
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        length = editText.text.length
    }

    override fun afterTextChanged(editable: Editable) {
        val currentLength = editText.text.length
        val ignoreBecauseIsDeleting = length > currentLength
        if (ignoreBecauseIsDeleting && editable.startsWith(MONTH_YEAR_DIVIDER)) {
            return
        }
        editable.run {
            when {
                length == FIRST_CHARACTER_INPUT && !isCharValidMonth(this[ZERO]) -> {
                    editText.setText(editText.context.getString(R.string.custom_month_year_separator, this[ZERO].toString()))
                }

                length == FIRST_CHARACTER_INPUT && this[ZERO].toString() == MONTH_YEAR_DIVIDER -> {
                    editText.setText(EMPTY_STRING)
                }

                length == SECOND_CHARACTER_INPUT && this[length - FIRST_CHARACTER_INPUT].toString() == MONTH_YEAR_DIVIDER -> {
                    editText.setText(editText.context.getString(R.string.first_month_and_separator_input_by_user, this.toString()))
                }

                !ignoreBecauseIsDeleting && this.length == SECOND_CHARACTER_INPUT && this[length - 1].toString() != MONTH_YEAR_DIVIDER -> {
                    editText.setText(editText.context.getString(R.string.only_add_separator, editText.text.toString()))
                }

                length == THIRD_CHARACTER_INPUT && this[length - FIRST_CHARACTER_INPUT].toString() != MONTH_YEAR_DIVIDER
                        && !ignoreBecauseIsDeleting -> {
                    insert(SECOND_CHARACTER_INPUT, MONTH_YEAR_DIVIDER)
                    editText.setText(this.toString())
                }

                length > THIRD_CHARACTER_INPUT && !isCharValidMonth(this[ZERO]) -> {
                    editText.setText(editText.context.getString(R.string.custom_month_year_separator, editable))
                }
            }
        }

        if (!ignoreBecauseIsDeleting) {
            editText.setSelection(editText.text.toString().length)
        }
    }

    private fun isCharValidMonth(charFromString: Char): Boolean {
        var month = ZERO
        if (charFromString.isDigit()) {
            month = charFromString.toString().toInt()
        }
        return month <= 1
    }

    companion object {
        private const val FIRST_CHARACTER_INPUT = 1
        private const val SECOND_CHARACTER_INPUT = 2
        private const val THIRD_CHARACTER_INPUT = 3
    }
}
