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
//        runStartModule()

    }

    inner class FlowAuthModels {
//        var modelMap = MapModel()

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


    fun BaseModule.startLoadingFlow() {
        coordinator.start()
        onDeInit?.invoke()
    }
}