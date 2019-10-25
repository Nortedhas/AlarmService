package com.ageone.alarm.External.Libraries.WebView

import android.webkit.WebView
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.External.Base.Module.BaseModule
import yummypets.com.stevia.fillHorizontally
import yummypets.com.stevia.fillVertically
import yummypets.com.stevia.subviews

fun BaseModule.openUrl(url: String) {
    val webView = WebView(currentActivity)
    innerContent.subviews(
        webView
    )

    webView
        .fillHorizontally()
        .fillVertically()

    webView.settings.javaScriptEnabled = true
    webView.loadUrl(url)
}