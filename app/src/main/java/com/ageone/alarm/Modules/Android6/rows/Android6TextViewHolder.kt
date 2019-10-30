package com.example.ageone.Modules.Android6.rows

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.Base.TextView.BaseTextView
import yummypets.com.stevia.*

class Android6TextViewHolder(val constraintLayout: ConstraintLayout) :
    BaseViewHolder(constraintLayout) {

    val textName by lazy {
        val text = BaseTextView()
        text.textSize = 30F
        text.textColor = Color.WHITE
        text.typeface = Typeface.DEFAULT_BOLD
        text.gravity = Gravity.CENTER
        text

    }
    val textNumber by lazy {
        val text = BaseTextView()
        text.textSize = 20F
        text.textColor = Color.WHITE
        text.typeface = Typeface.DEFAULT
        text.gravity = Gravity.CENTER
        text

    }

    init {

        renderUI()
    }

}

fun Android6TextViewHolder.renderUI() {
    constraintLayout.subviews(
        textName,
        textNumber

    )

    textName
        .fillHorizontally()
        .constrainTopToTopOf(constraintLayout)
        .constrainBottomToBottomOf(constraintLayout)

    textNumber
        .fillHorizontally()
        .constrainTopToBottomOf(textName,4)
        .constrainBottomToBottomOf(constraintLayout,16)





}

fun Android6TextViewHolder.initialize(Name:String,Number:String) {
    textName.text = Name
    textNumber.text = Number

}
