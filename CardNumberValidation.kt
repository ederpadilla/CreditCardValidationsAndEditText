package mx.justo.styling.widget.card

object CardNumberValidation {

    fun isValidCardNumber(cardNumber: String): Boolean {
        if (cardNumber.isBlank()) return false
        val normalizedNumber = removeSpacesAndHyphens(cardNumber)
        return isValidLuhnNumber(normalizedNumber) && isValidCardLength(normalizedNumber)
    }

    private fun removeSpacesAndHyphens(cardNumberWithSpaces: String) =
            if (cardNumberWithSpaces.isBlank()) {
                EMPTY_STRING
            } else cardNumberWithSpaces!!.replace("\\s|-".toRegex(), EMPTY_STRING)

    private fun isValidLuhnNumber(cardNumber: String): Boolean {
        var isOdd = true
        var sum = ZERO

        for (index in cardNumber.length - FIRST_POSITION downTo ZERO) {
            val cardChar = cardNumber[index]
            if (!Character.isDigit(cardChar)) {
                return false
            }
            var digitInteger = Character.getNumericValue(cardChar)
            isOdd = !isOdd
            if (isOdd) {
                digitInteger *= POSITION_TWO
            }
            if (digitInteger > MAX_DIGIT_INT) {
                digitInteger -= MAX_DIGIT_INT
            }

            sum += digitInteger
        }

        return sum % TEN == ZERO
    }

    private fun isValidCardLength(cardNumber: String) =
            if (cardNumber.isEmpty()) {
                false
            } else isValidCardLength(cardNumber, getPossibleCardType(cardNumber))

    private fun isValidCardLength(cardNumber: String, cardBrand: String) = cardNumber.length.run {
        when (cardBrand) {
            AMERICAN_EXPRESS -> this == LENGTH_AMERICAN_EXPRESS
            DINERS_CLUB -> this == LENGTH_DINERS_CLUB
            else -> this == LENGTH_COMMON_CARD
        }
    }

    private fun getPossibleCardType(cardNumber: String) =
            when (cardNumber) {
                in PREFIXES_AMERICAN_EXPRESS -> AMERICAN_EXPRESS
                in PREFIXES_DISCOVER -> DISCOVER
                in PREFIXES_JCB -> JCB
                in PREFIXES_DINERS_CLUB -> DINERS_CLUB
                in PREFIXES_VISA -> VISA
                in PREFIXES_MASTERCARD -> MASTERCARD
                else -> CardNumberStringAdapter.UNKNOWN
            }
}
