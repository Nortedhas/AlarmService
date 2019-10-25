package com.ageone.alarm.External.Libraries.Alert

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.ageone.alarm.Application.coordinator
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.External.Base.TextView.BaseTextView
import yummypets.com.stevia.*

val alertManager
    get() = AlertManager()

class AlertManager {

    fun renderUI() {

        constraintLayout.subviews(
            imageViewIcon,
            textViewTitle,
            textViewMessage
        )

        imageViewIcon
            .constrainTopToTopOf(constraintLayout, 8)
            .constrainLeftToLeftOf(constraintLayout, 16)
            .height(20F.dp)
            .width(20F.dp)

        textViewTitle
            .fillHorizontally()
            .constrainLeftToLeftOf(constraintLayout, 16)
            .constrainTopToBottomOf(imageViewIcon, 8)
            .constrainRightToRightOf(constraintLayout, 16)

        textViewMessage
            .fillHorizontally()
            .constrainLeftToLeftOf(constraintLayout, 16)
            .constrainTopToBottomOf(textViewTitle, 8)
            .constrainRightToRightOf(constraintLayout, 16)

    }

    // MARK: UI

    val constraintLayout: ConstraintLayout by lazy {
        val constraintLayout = ConstraintLayout(currentActivity)
        constraintLayout
    }

    val imageViewIcon: ImageView by lazy {
        val imageViewIcon = ImageView(currentActivity)
        imageViewIcon
    }

    // MARK: viewHolder

    val textViewTitle: BaseTextView by lazy {
        val textViewTitle = BaseTextView()
        textViewTitle.gravity = View.TEXT_ALIGNMENT_VIEW_END
        textViewTitle.typeface = Typeface.DEFAULT_BOLD
        textViewTitle.textSize = 6F.dp
        textViewTitle.textColor = Color.BLACK
        textViewTitle.setBackgroundColor(Color.TRANSPARENT)
        textViewTitle

    }

    // MARK: textViewMessage

    val textViewMessage: BaseTextView by lazy {
        val textViewMessage = BaseTextView()
        textViewMessage.gravity = View.TEXT_ALIGNMENT_VIEW_END
        textViewMessage.typeface = Typeface.DEFAULT_BOLD
        textViewMessage.textSize = 5F.dp
        textViewMessage.textColor = Color.DKGRAY
        textViewMessage.setBackgroundColor(Color.TRANSPARENT)
        textViewMessage
    }

}

// MARK: Single

fun AlertManager.single(title: String, message: String, image: Int? = null,
                        button: String = "OK", completion: ((DialogInterface, Int) -> (Unit))? = null) {
    renderUI()

    if (image == null) {
        imageViewIcon
            .height(0)
            .width(0)
            .constrainTopToTopOf(constraintLayout, 0)
    } else {
        imageViewIcon.setImageResource(image)
    }

    textViewTitle.text = title
    textViewMessage.text = message

    val builder = AlertDialog.Builder(currentActivity)
    builder.setView(constraintLayout)
    completion?.let{
        builder.setPositiveButton(button, completion)
    }
    builder.show()

}

// MARK: Double

fun AlertManager.double(
    title: String, message: String, image: Int? = null,
    button: String = "OK", completion: (DialogInterface, Int) -> (Unit),
    buttonCancel: String = "CANCEL", completionCancel: ((DialogInterface, Int) -> (Unit))? = null
) {

    renderUI()

    if (image == null) {
        imageViewIcon
            .height(0)
            .width(0)
            .constrainTopToTopOf(constraintLayout, 0)
    } else {
        imageViewIcon.setImageResource(image)
    }

    textViewTitle.text = title
    textViewMessage.text = message

    val builder = AlertDialog.Builder(currentActivity)
    builder.setView(constraintLayout)
    builder.setPositiveButton(button, completion)
    completionCancel?.let {
        builder.setNegativeButton(buttonCancel, completionCancel)
    }
    builder.show()
}

// MARK: With list

fun AlertManager.list(title: String,variants: Array<String>, completion: (DialogInterface, Int) -> (Unit)) {

    // setup the alert builder
    val builder = AlertDialog.Builder(currentActivity)
    builder.setTitle(title)

    // add a list
    builder.setItems(variants, completion)

    // create and show the alert dialog
    val dialog = builder.create()
    dialog.show()

}

// MARK: block ui

fun AlertManager.blockUI(isVisible: Boolean) {

    if (isVisible) {
        coordinator.blockConstraint.visibility = View.VISIBLE
        coordinator.circularProgress.visibility = View.VISIBLE
    } else {
        coordinator.blockConstraint.visibility = View.GONE
        coordinator.circularProgress.visibility = View.GONE
    }

}