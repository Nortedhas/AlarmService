package com.ageone.alarm.External.Base.RadioButton

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.TintableCompoundButton
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.R


class BaseRadioButton: AppCompatRadioButton(currentActivity) {

    var colors: IntArray? = null //uncheckedColor, checkedColor

    fun initialize() {
        colors?.let { colors ->
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked), // unchecked
                    intArrayOf(android.R.attr.state_checked)  // checked
                ),
                colors
            )
            (this as TintableCompoundButton).supportButtonTintList = colorStateList
        }
    }

    fun setButtonBottom(){
        buttonDrawable = null
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, getButton())
        gravity = Gravity.CENTER
    }

}

private fun getButton(): Drawable? {
    val a =
        currentActivity?.theme?.obtainStyledAttributes(
            R.style.AppTheme,
            intArrayOf(android.R.attr.listChoiceIndicatorSingle)
        )
    val attributeResourceId = a?.getResourceId(0, 0)
    val drawable = currentActivity?.resources?.getDrawable(attributeResourceId ?: 0)
    drawable?.setTintList(getStateList(Color.WHITE, Color.rgb(0x70,0x7A,0xBA)))

    return drawable
}

private fun getStateList(uncheckedColor: Int, checkedColor: Int) = ColorStateList(
    arrayOf(
        intArrayOf(-android.R.attr.state_checked), // unchecked
        intArrayOf(android.R.attr.state_checked)  // checked
    ),
    intArrayOf(uncheckedColor, checkedColor)
)