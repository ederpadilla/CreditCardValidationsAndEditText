package mx.justo.styling.widget.card

object CVVValidation {

    fun isValidCvv(cvv: String, cardNumber: String): Boolean {
        if (cvv.isEmpty()) { return false }
        val cvcValue = cvv.trim { it <= EMPTY_CHAR }
        val updatedType = CardNumberStringAdapter(cardNumber).getBrand()

        return cvcValue.length in MIN_LENGTH..MAX_LENGTH || AMERICAN_EXPRESS == updatedType &&
                cvcValue.length == MAX_LENGTH || cvcValue.length == MIN_LENGTH
    }
}
