package mx.justo.styling.widget.card

object CardDateExpValidation {

    fun isValidDate(date: String) = isValidDateLength(date) && isValidFormat(date)

    fun getMoth(date: String) = separateMonthAndYear(date)[ZERO]

    fun getYear(date: String) = separateMonthAndYear(date)[FIRST_POSITION]

    private fun isValidDateLength(date: String) = date.isNotBlank() && date.length > MAX_LENGTH

    private fun isValidFormat(date: String) = date[POSITION_TWO] == MONTH_YEAR_DIVIDER_CHAR

    private fun separateMonthAndYear(date: String): List<String> = date.split(MONTH_YEAR_DIVIDER)
}
