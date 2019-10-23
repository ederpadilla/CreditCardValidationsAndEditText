package mx.justo.styling.widget.card

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import mx.justo.styling.widget.card.CardNumberStringAdapter.Companion.AMERICAN_EXPRESS
import mx.justo.styling.widget.card.CardNumberStringAdapter.Companion.DINERS_CLUB

class CardNumberTextWatcher : TextWatcher {

    private var selfChange = false

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

    override fun afterTextChanged(editable: Editable) {
        if (!selfChange) {
            selfChange = true
            val typeCard = getTypeCard(editable.toString())
            editable.filters = setNewLength(typeCard)
            val numberCardFormat = formatCreditCard(editable.toString(), typeCard)
            editable.replace(ZERO, editable.length, numberCardFormat, ZERO, numberCardFormat.length)
            selfChange = false
        }
    }

    private fun getTypeCard(numberCard: String): String = if (numberCard.isNotEmpty()) {
        CardNumberStringAdapter(numberCard).getBrand()
    } else {
        CardNumberStringAdapter.UNKNOWN
    }

    private fun setNewLength(typeCard: String): Array<InputFilter> {
        val length = if (typeCard == AMERICAN_EXPRESS || typeCard == DINERS_CLUB) {
            AMERICAN_EXPRESS_DINERS_CLUB_LENGTH
        } else {
            DEFAULT_LENGTH
        }
        return arrayOf(InputFilter.LengthFilter(length))
    }

    private fun formatCreditCard(numberCard: String, typeCard: String): String {
        val formatBuilder = StringBuilder()
        val numbers = numberCard.replace("\\s+".toRegex(), EMPTY_STRING)

        if (typeCard == AMERICAN_EXPRESS) {
            numbers.forEachIndexed { index, _ ->
                if (index > ZERO && (index == FIRST_SEPARATOR || index == AMERICAN_EXPRESS_SECOND_SEPARATOR)) {
                    formatBuilder.append(SPACE_DIVIDER)
                }
                formatBuilder.append(numbers[index])
            }
        } else {
            numbers.forEachIndexed { index, _ ->
                if (index > ZERO && index % FIRST_SEPARATOR == ZERO) {
                    formatBuilder.append(SPACE_DIVIDER)
                }
                formatBuilder.append(numbers[index])
            }
        }

        return formatBuilder.toString()
    }

    companion object {
        private const val AMERICAN_EXPRESS_DINERS_CLUB_LENGTH = 17
        private const val DEFAULT_LENGTH = 19
        private const val FIRST_SEPARATOR = 4
        private const val AMERICAN_EXPRESS_SECOND_SEPARATOR = 10
    }
}
