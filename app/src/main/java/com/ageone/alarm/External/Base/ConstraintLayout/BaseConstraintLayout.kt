package com.ageone.alarm.External.Base.ConstraintLayout

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.Application.utils
import com.ageone.alarm.External.Base.Button.BaseButton
import com.ageone.alarm.External.Base.RecyclerView.BaseRecyclerView
import com.ageone.alarm.External.Base.TextInputLayout.BaseTextInputEditText
import com.ageone.alarm.External.Base.TextInputLayout.BaseTextInputLayout
import timber.log.Timber
import yummypets.com.stevia.constrainBottomToBottomOf

class BaseConstraintLayout: ConstraintLayout(currentActivity) {

}

fun BaseConstraintLayout.setButtonAboveKeyboard(button: BaseButton) {
    val rectLayout = Rect()
    getWindowVisibleDisplayFrame(rectLayout)

    var usableRect = rectLayout.bottom - rectLayout.top



    viewTreeObserver.addOnGlobalLayoutListener {

        val statusBarInDp = (utils.variable.statusBarHeight.toFloat() / rootView.height.toFloat()) * utils.variable.displayHeight.toFloat()

        var layoutHeight = 0

        if(statusBarInDp > 24) {
            layoutHeight = usableRect
        } else {
            layoutHeight = usableRect - utils.variable.statusBarHeight
        }

        val rect = Rect()
        getWindowVisibleDisplayFrame(rect)

        var displayHeight = rootView.height
        var usableHeight = rect.bottom - rect.top
        var bottomButton = displayHeight - layoutHeight

        var keyboardHeight = displayHeight - usableHeight - bottomButton
        var coff: Float = keyboardHeight.toFloat() / layoutHeight
        var heightInDp = utils.variable.displayHeight

        var margin = 0F

        if(statusBarInDp > 24) {
            margin = (heightInDp * coff)
        } else {
           margin = (heightInDp * coff) - 8
        }


            if (margin > 50) {
                button.constrainBottomToBottomOf(this, margin.toInt())
            } else if (margin < 50) {
                button.constrainBottomToBottomOf(this)
            }
    }
}

fun BaseConstraintLayout.dismissFocus(view: EditText?) {

    viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        val height: Float = rootView.height.toFloat()

        getWindowVisibleDisplayFrame(rect)

        var heightDiff =
            ((((height - rect.height()) / height) * utils.variable.displayHeight).toInt())

        if (heightDiff < 50) {
            view?.clearFocus()
            view?.isFocusableInTouchMode = true
        }
    }
}

fun BaseConstraintLayout.setScrollableView(view: BaseTextInputLayout?, recyclerView: BaseRecyclerView){
    val rectLayout = Rect()
    getWindowVisibleDisplayFrame(rectLayout)

    var usableRect = rectLayout.bottom - rectLayout.top

    var isFocus = false


    viewTreeObserver.addOnGlobalLayoutListener {

        val statusBarInDp = (utils.variable.statusBarHeight.toFloat() / rootView.height.toFloat()) * utils.variable.displayHeight.toFloat()

        var layoutHeight = 0

        if(statusBarInDp > 24) {
            layoutHeight = usableRect
        } else {
            layoutHeight = usableRect - utils.variable.statusBarHeight
        }

        val rect = Rect()
        getWindowVisibleDisplayFrame(rect)

        var displayHeight = rootView.height

        var usableHeight = rect.bottom - rect.top
        var bottomButton = displayHeight - layoutHeight

        var keyboardHeight = displayHeight - usableHeight - bottomButton
        var coff: Float = keyboardHeight.toFloat() / layoutHeight
        var heightInDp = utils.variable.displayHeight

        var margin = 0F

        if(statusBarInDp > 24) {
            margin = (heightInDp * coff)
        } else {
            margin = (heightInDp * coff) - 8
        }

        if (margin > 50) {
            recyclerView.constrainBottomToBottomOf(this,margin.toInt())
            isFocus = true
        } else if (margin.toInt() < 50) {
            recyclerView.constrainBottomToBottomOf(this)
            if(isFocus){
                view?.editText?.clearFocus()
                isFocus = false
            }
        }

    }

}

