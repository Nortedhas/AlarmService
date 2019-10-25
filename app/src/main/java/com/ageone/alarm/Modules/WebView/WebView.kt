package com.ageone.alarm.Modules

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ageone.alarm.R
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.InitModuleUI
import yummypets.com.stevia.fillHorizontally
import yummypets.com.stevia.fillVertically
import yummypets.com.stevia.subviews

class WebView(initModuleUI: InitModuleUI = InitModuleUI(), url: String) : BaseModule(initModuleUI) {
    val webView by lazy {
        val view = WebView(currentActivity)
        view
    }

    init {
//        setBackgroundResource(R.drawable.back_white)//TODO: set back
        toolbar.title = "Оплата"
        renderToolbar()

        innerContent.subviews(
            webView
        )

        webView
            .fillHorizontally()
            .fillVertically()

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        webView.webViewClient = MyWebViewClient()
    }
}

class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }
}
