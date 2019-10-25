package com.ageone.alarm.UIComponents.ViewHolders

import android.graphics.Color
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.Base.TextView.BaseTextView
import com.ageone.alarm.External.Base.View.BaseView
import yummypets.com.stevia.*

class KitchenTextViewHolder(val constraintLayout: ConstraintLayout): BaseViewHolder(constraintLayout) {
    val uncheckColor = Color.WHITE
    val checkColor = Color.parseColor("#FFEF9D")

    val back by lazy {
        val view = BaseView()
        view.cornerRadius = 20.dp
        view.initialize()
        view
    }

    val textViewKitchen by lazy {
        val textView = BaseTextView()
        textView.textSize = 14F
        textView.textColor = Color.BLACK
        textView.initialize()
        textView
    }

    init {

        renderUI()
    }
}

fun KitchenTextViewHolder.renderUI() {
    constraintLayout
        .width(wrapContent)
        .height(50)

    constraintLayout.subviews(
        back.subviews(
            textViewKitchen
        )
    )

    back
        .height(25)
        .constrainTopToTopOf(constraintLayout,17)
        .constrainLeftToLeftOf(constraintLayout,12)

    textViewKitchen
        .constrainRightToRightOf(back, 8)
        .constrainLeftToLeftOf(back, 8)
        .constrainTopToTopOf(back, 4)
        .constrainBottomToBottomOf(back, 4)

}

fun KitchenTextViewHolder.initialize(kitchen: String, isChecked: Boolean) {

    textViewKitchen.text = kitchen
    back.backgroundColor = if (isChecked) checkColor else uncheckColor
    back.initialize()

}
