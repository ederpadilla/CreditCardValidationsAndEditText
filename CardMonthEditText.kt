package mx.justo.styling.widget.card

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

class CardMonthEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                  defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle) :
        EditText(context, attrs, defStyleAttr) {

    init {
        addTextChangedListener(MonthAndYearTextWatcher(this))
    }
}
