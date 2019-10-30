package com.example.ageone.Modules.LoadingScreen


import android.os.CountDownTimer
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.InitModuleUI
import com.ageone.alarm.R
import yummypets.com.stevia.subviews

class LoadingScreenView(initModuleUI: InitModuleUI = InitModuleUI()): BaseModule(initModuleUI) {

    val viewModel = LoadingScreenViewModel()

    init {

        setBackgroundResource(R.drawable.back_start)
        renderUIO()
    }

}

fun LoadingScreenView.renderUIO() {

    innerContent.subviews(
    )


    var timer = object : CountDownTimer(3000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            millisUntilFinished / 1000

        }

        override fun onFinish() {
            emitEvent?.invoke(LoadingScreenViewModel.EventType.OnTimerStop.name)
        }
    }

    timer.start()
}
