package com.ageone.alarm.External.Base.TextView

import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.text.InputFilter
import android.widget.TextView
import com.ageone.alarm.Application.currentActivity

class BaseTextView(): TextView(currentActivity) {

    var gradientDrawable = GradientDrawable()

    var cornerRadius: Int? = null
    var backgroundColor: Int? = null

    var gradient: Int? = null
    var orientation: GradientDrawable.Orientation? = null

    var borderColor: Int? = null
    var borderWidth: Int? = null

    fun initialize() {

        gradientDrawable.shape = GradientDrawable.RECTANGLE

        cornerRadius?.let { cornerRadius ->
            gradientDrawable.cornerRadius = cornerRadius.toFloat()
        }

        borderWidth?.let { borderWidth ->
            borderColor?.let { borderColor ->
                gradientDrawable.setStroke(borderWidth, borderColor)
            }
        }

        backgroundColor?.let { backgroundColor ->
            gradientDrawable.setColor(backgroundColor)
            gradient?.let { gradient ->
                gradientDrawable.colors = intArrayOf(backgroundColor, gradient)
            }
        }

        orientation?.let { orientation ->
            gradientDrawable.orientation = orientation
        }

        background = gradientDrawable
    }

}

/*
val text by lazy {
    val text = BaseTextView()
    text.text = "elevation"
    val gradientDrawable = GradientDrawable()
    gradientDrawable.gradientDrawable = GradientDrawable.RECTANGLE
    gradientDrawable.setColor(Color.parseColor("#30bcff"))
    text.backgroundColor = gradientDrawable
    text.width(25F.dp)
    text.elevation = 8F.dp
    text
}*/
fun TextView.underline() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun BaseTextView.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}
