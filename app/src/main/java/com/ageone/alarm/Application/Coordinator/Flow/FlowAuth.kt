package com.ageone.alarm.Application.Coordinator.Flow

import android.graphics.Color
import android.os.Handler
import androidx.core.view.size
import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator.ViewFlipperFlowObject.viewFlipperFlow
import com.ageone.alarm.Application.Coordinator.Router.DataFlow
import com.ageone.alarm.Application.coordinator
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.External.Base.Flow.BaseFlow
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.Extensions.Activity.setLightStatusBar
import com.ageone.alarm.External.InitModuleUI
import com.example.ageone.Modules.Android6.Android6Model
import com.example.ageone.Modules.Android6.Android6View
import com.example.ageone.Modules.Android6.Android6ViewModel
import com.example.ageone.Modules.LoadingScreen.LoadingScreenModel
import com.example.ageone.Modules.LoadingScreen.LoadingScreenView
import com.example.ageone.Modules.LoadingScreen.LoadingScreenViewModel

fun FlowCoordinator.runFlowAuth() {

    var flow: FlowAuth? = FlowAuth()

    flow?.let{ flow ->

        viewFlipperFlow.addView(flow.viewFlipperModule)
        viewFlipperFlow.displayedChild = viewFlipperFlow.indexOfChild(flow.viewFlipperModule)

        currentActivity?.setLightStatusBar(Color.WHITE,Color.GRAY)

        flow.settingsCurrentFlow = DataFlow(viewFlipperFlow.size - 1)

    }

    flow?.onFinish = {
        viewFlipperFlow.removeView(flow?.viewFlipperModule)
        flow?.viewFlipperModule?.removeAllViews()
        flow = null
    }


    flow?.start()

}

class FlowAuth: BaseFlow() {

    private var models = FlowAuthModels()

    override fun start() {
        onStarted()
        runModuleStart()

    }

    inner class FlowAuthModels {
        var modelStart = LoadingScreenModel()
        var modelAndroid6 = Android6Model()

    }


    /*fun runModuleRegistration(){
        val module = RegistrationView(InitModuleUI(
            isBottomNavigationVisible = false,
            isToolbarHidden = false,
            isBackPressed = true
        ))

        module.emitEvent = { event ->
            when(RegistrationViewModel.EventType.valueOf(event)){
                RegistrationViewModel.EventType.OnNextPressed ->{
                    runModuleSMS()

                }
            }
        }

        module.viewModel.initialize(models.modelEntry) { module.reload() }

        settingsCurrentFlow.isBottomNavigationVisible = false

        push(module)
    }*/
    fun runModuleStart() {
        val module = LoadingScreenView(InitModuleUI(
            isBottomNavigationVisible = false,
            isToolbarHidden = true
        ))
        module.viewModel.initialize(models.modelStart) { module.reload() }

        settingsCurrentFlow.isBottomNavigationVisible = false

        module.emitEvent = { event ->
            when(LoadingScreenViewModel.EventType.valueOf(event)) {
                LoadingScreenViewModel.EventType.OnTimerStop -> {
                    runModuleAndroid6()

                }

            }
        }
        push(module)
    }

    fun runModuleAndroid6() {
        val module = Android6View(InitModuleUI(
            isBottomNavigationVisible = false,
            isToolbarHidden = true
        ))
        module.viewModel.initialize(models.modelAndroid6) { module.reload() }

        settingsCurrentFlow.isBottomNavigationVisible = false

        module.emitEvent = { event ->
            when(Android6ViewModel.EventType.valueOf(event)) {
                Android6ViewModel.EventType.OnClickButtonAndroid6 -> {
                    module.startLoadingFlow()
                }

            }
        }
        push(module)
    }

    fun BaseModule.startLoadingFlow() {
        coordinator.start()
        onDeInit?.invoke()
    }
}