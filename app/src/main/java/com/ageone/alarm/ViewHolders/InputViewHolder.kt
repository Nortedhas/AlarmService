package com.ageone.alarm.UIComponents.ViewHolders

import android.graphics.Color
import android.os.Handler
import android.view.KeyEvent
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import com.ageone.alarm.External.Base.RecyclerView.BaseViewHolder
import com.ageone.alarm.External.Base.TextInputLayout.BaseTextInputLayout
import com.ageone.alarm.External.Base.TextInputLayout.InputEditTextType
import com.google.android.material.textfield.TextInputLayout
import yummypets.com.stevia.*


class InputViewHolder(val constraintLayout: ConstraintLayout) : BaseViewHolder(constraintLayout) {

    val textInputL by lazy {
        val textInput = BaseTextInputLayout()

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.marginStart = (-2).dp
        params.updateMargins(left = (-2).dp)
        textInput.layoutParams = params


        textInput.boxStrokeColor = Color.parseColor("#D7D7D7")
        textInput.setInactiveUnderlineColor(Color.rgb(215, 215, 215))

        textInput.editText?.let { editText ->
            editText.textColor = Color.parseColor("#333333")
            editText.textSize = 7F.dp

            editText.setOnTouchListener { view, motionEvent ->
                if(motionEvent.action == KeyEvent.ACTION_DOWN ){
                    Handler().postDelayed({
                        editText.requestFocus()
                    }, 300)
                }
                false
            }

        }
        textInput
    }

    init {

        renderUI()
    }

}

fun InputViewHolder.renderUI() {
    constraintLayout.subviews(
        textInputL
    )

    textInputL
        .constrainTopToTopOf(constraintLayout)
        .fillHorizontally(14)
}

fun InputViewHolder.initialize(hint: String, type: InputEditTextType) {
    textInputL.hint = hint
    textInputL.defineType(type)
}