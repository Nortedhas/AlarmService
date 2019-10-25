package com.ageone.alarm.External.Extensions.Activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import com.ageone.alarm.Application.utils
import timber.log.Timber

fun Activity.hideKeyboard() {
    // Check if no view has
    val view = currentFocus
    view?.let { v ->
        //hide keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

fun Activity.setDisplaySize() {
    val displayMetrics = resources.displayMetrics
    utils.variable.displayWidth = (displayMetrics.widthPixels / displayMetrics.density).toInt()
    utils.variable.displayHeight = (displayMetrics.heightPixels / displayMetrics.density).toInt()

    Timber.i("width = ${utils.variable.displayWidth}")

    // Calculate ActionBar height
    val tv = TypedValue()
    if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        utils.variable.actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics) / 3
    }
}

fun Activity.copyToClipboard(text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label", text)
    clipboard.primaryClip = clip
}