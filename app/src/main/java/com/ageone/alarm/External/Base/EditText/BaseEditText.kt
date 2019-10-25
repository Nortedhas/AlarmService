package com.ageone.alarm.External.Base.EditText

import android.text.InputFilter
import android.widget.EditText
import com.ageone.alarm.Application.currentActivity

class BaseEditText: EditText(currentActivity) {

}

fun EditText.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}

fun EditText.phoneMask(s: CharSequence?) = when (val l = s?.length ?: 40) {
    1 -> when (s?.last() ?: '0') {
            '7', '8' -> "+7"
            '9' -> "+7 (9"
            else -> ""
        }

    2 -> when (s?.last() ?: '0') {
            '9' -> "+7 (9"
            else -> "+7"
        }

    in (3..5) -> "+7 (9"

    7 -> this.text.toString() + ") "

    8, 9 -> {
        val last = this.text?.last()
        this.text.toString().dropLast(l - 7) + ") " + last
    }

    13, 16 -> {
        val last = this.text?.last()
        this.text.toString().dropLast(1) + "-" + last
    }

    else -> this.text

}