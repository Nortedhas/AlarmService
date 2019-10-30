package com.example.ageone.Modules.Android6.rows

import android.graphics.Color
import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.Base.TextView.BaseTextView
import com.ageone.alarm.External.Base.View.BaseView
import yummypets.com.stevia.*

class Android6CardViewHolder(val constraintLayout: ConstraintLayout) : BaseViewHolder(constraintLayout) {

    val background by lazy {
        val view = BaseView()
        view.cornerRadius = 15.dp
        view.backgroundColor = Color.parseColor("#353244")
        view.gradientDrawable.setStroke(5, Color.argb(27, 148, 185, 255))
        view.initialize()
        view
    }
    val titleText by lazy {
        val text = BaseTextView()
        text.textSize = 16F
        text.gravity = Gravity.START
        text.textColor = Color.WHITE
        text
    }

    init {


        renderUI()
    }

}

fun Android6CardViewHolder.renderUI() {
    constraintLayout.subviews(
        background.subviews(
            titleText
        )
    )

    background
        .fillHorizontally(20)
        .constrainTopToTopOf(constraintLayout,16)
        .height(90)

    titleText
        .fillHorizontally(16)
        .constrainTopToTopOf(background,17)


}

fun Android6CardViewHolder.initialize(textTitle:String) {
    titleText.text = textTitle

}
