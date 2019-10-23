package mx.justo.styling.widget.card

class CardNumberStringAdapter(number: String) {

    private val number = normalizeCardNumber(number)

    fun getBrand(): String {
        var brand = EMPTY_STRING
        if (!number.isBlank()) {
            val evaluatedType: String = when (number) {
                in PREFIXES_AMERICAN_EXPRESS -> AMERICAN_EXPRESS
                in PREFIXES_DISCOVER -> DISCOVER
                in PREFIXES_JCB -> JCB
                in PREFIXES_DINERS_CLUB -> DINERS_CLUB
                in PREFIXES_VISA -> VISA
                in PREFIXES_MASTERCARD -> MASTERCARD
                else -> UNKNOWN
            }
            brand = evaluatedType
        }
        return brand
    }

    private fun normalizeCardNumber(number: String) = number.trim { it <= EMPTY_CHAR }.replace("\\s+|-".toRegex(), EMPTY_STRING)

    companion object {
        private const val DISCOVER = "Discover"
        private const val JCB = "JCB"
        private const val VISA = "Visa"
        private const val MASTERCARD = "MasterCard"
        const val UNKNOWN = "Unknown"
        const val AMERICAN_EXPRESS = "American Express"
        const val DINERS_CLUB = "Diners Club"
      }
}
