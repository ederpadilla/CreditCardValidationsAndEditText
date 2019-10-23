package mx.justo.styling.widget.card

import mx.justo.styling.widget.card.MonthAndYearValidations.Clock.Companion.getCalendarInstance
import java.util.Calendar
import java.util.Locale

object MonthAndYearValidations {

    fun isValidateExpiryDate(expMonth: String, expYear: String): Boolean {
        if (!validateExpMonth(expMonth)) {
            return false
        }
        return if (!validateExpYear(expYear)) {
            false
        } else !hasMonthPassed(expYear, expMonth)
    }

    private fun hasMonthPassed(year: String, month: String): Boolean {
        if (hasYearPassed(year)) {
            return true
        }
        val now = getCalendarInstance()
        return normalizeYear(year.toInt()) == now?.get(Calendar.YEAR) && month.toInt() < now.get(Calendar.MONTH) + FIRST_POSITION
    }

    private fun validateExpMonth(month: String) = month.toInt() in FIRST_POSITION..LAST_MONTH

    private fun validateExpYear(expYear: String) = !hasYearPassed(expYear)

    private fun hasYearPassed(year: String) = normalizeYear(year.toInt()) < getCalendarInstance()?.get(Calendar.YEAR)!!

    private fun normalizeYear(year: Int): Int {
        var normalizedYear = year
        if (normalizedYear in ZERO..MAX_RANGE) {
            val now = getCalendarInstance()
            val currentYear = now?.get(Calendar.YEAR).toString()
            val prefix = currentYear.substring(ZERO, currentYear.length - POSITION_TWO)
            normalizedYear = (String.format(Locale.US, FORMAT, prefix, normalizedYear)).toInt()
        }
        return normalizedYear
    }

    private class Clock {

        private var calendarInstance: Calendar? = null

        private fun getCalendarInstance() =
                if (calendarInstance != null) calendarInstance?.clone() as Calendar
                else Calendar.getInstance()

        companion object {

            private var instance: Clock? = null

            fun getCalendarInstance(): Calendar? = getInstance()?.getCalendarInstance()

            private fun getInstance() =
                    if (instance != null) instance else Clock()
        }
    }
}
