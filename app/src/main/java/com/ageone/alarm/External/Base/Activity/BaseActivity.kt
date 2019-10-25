package com.ageone.alarm.External.Base.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

open class BaseActivity: AppCompatActivity {

    constructor(

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("${this.localClassName} onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("${this.localClassName} onStart")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("${this.localClassName} onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("${this.localClassName} onRestart")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("${this.localClassName} onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("${this.localClassName} onDestroy")
    }
}