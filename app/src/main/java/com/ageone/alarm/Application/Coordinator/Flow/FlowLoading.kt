package com.ageone.alarm.Application.Coordinator.Flow

import androidx.core.view.size
import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator.ViewFlipperFlowObject.viewFlipperFlow
import com.ageone.alarm.Application.Coordinator.Router.DataFlow
import com.ageone.alarm.Application.Coordinator.Router.TabBar.TabBar
import com.ageone.alarm.Application.Coordinator.Router.createStackFlows
import com.ageone.alarm.External.Base.Flow.BaseFlow
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.InitModuleUI
import com.ageone.alarm.Modules.LoadingModel
import com.ageone.alarm.Modules.LoadingView
import com.ageone.alarm.Modules.LoadingViewModel
import timber.log.Timber

fun FlowCoordinator.runFlowLoading() {

    var flow: FlowLoading? = FlowLoading()

    flow?.let{ flow ->

        viewFlipperFlow.addView(flow.viewFlipperModule)
        viewFlipperFlow.displayedChild = viewFlipperFlow.indexOfChild(flow.viewFlipperModule)

        flow.settingsCurrentFlow = DataFlow(viewFlipperFlow.size - 1)

    }

    flow?.onFinish = {
        viewFlipperFlow.removeView(flow?.viewFlipperModule)
        flow?.viewFlipperModule?.removeAllViews()

        // MARK: first appear flow in bottom bar

        Timber.i("Bottom Start flow create")
        val startFlow = 0
        createStackFlows(startFlow)
        TabBar.createBottomNavigation()
        TabBar.bottomNavigation.currentItem = startFlow
        viewFlipperFlow.displayedChild = startFlow

        flow = null
    }

    flow?.start()

}

class FlowLoading: BaseFlow() {

    private var models = FlowLoadingModels()

    override fun start() {
        onStarted()
        runModuleLoading()
    }

    inner class FlowLoadingModels {
        var modelLoading = LoadingModel()
    }

    //main load, parsing, socket

    fun runModuleLoading() {
        Timber.i("Bottom Run module loading")
        val module = LoadingView(InitModuleUI(
            isBottomNavigationVisible = false,
            isToolbarHidden = true
        ))
        module.viewModel.initialize(models.modelLoading) { module.reload() }

        settingsCurrentFlow.isBottomNavigationVisible = false

        module.emitEvent = { event ->
            when(LoadingViewModel.EventType.valueOf(event)) {
                LoadingViewModel.EventType.onFinish -> {

                    Timber.i("Bottom Start flow main")
                    module.startMainFlow()
                }
            }
        }
        push(module)

        module.loading()
    }

    fun BaseModule.startMainFlow() {
        Timber.i("Start main load")
//        coordinator.start()
        onFinish?.invoke()
    }
}