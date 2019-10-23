package mx.justo.styling.widget.card

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText

//TODO Not sure dont duplicate the custom view for Card number and Cardmonth, create a watcher property to set it in the fragment.
class CardNumberEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle) :
        EditText(context, attrs, defStyleAttr) {

    init {
        addTextChangedListener(CardNumberTextWatcher())
    }
}
