package com.ageone.alarm.Modules

import com.ageone.alarm.R
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.InitModuleUI
import timber.log.Timber
import yummypets.com.stevia.subviews

class LoadingView(initModuleUI: InitModuleUI = InitModuleUI()) : BaseModule(initModuleUI) {

    val viewModel = LoadingViewModel()

    init {
//        setBackgroundResource(R.drawable.back_white)//TODO: set back

        innerContent.subviews(
        )

        /*viewModel.startLoading {
            emitEvent?.invoke(LoadingViewModel.EventType.onFinish.toString())
        }
*/
        Timber.i("Bottom init loading view")

    }

    fun loading(){
        viewModel.startLoading {
            emitEvent?.invoke(LoadingViewModel.EventType.onFinish.name)
        }

    }



}
