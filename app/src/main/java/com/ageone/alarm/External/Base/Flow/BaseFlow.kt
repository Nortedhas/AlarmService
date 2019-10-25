package com.ageone.alarm.External.Base.Flow

import android.graphics.Color
import android.view.View
import androidx.core.view.contains
import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator.ViewFlipperFlowObject.currentFlow
import com.ageone.alarm.Application.Coordinator.Flow.setBottomNavigationVisible
import com.ageone.alarm.Application.Coordinator.Router.DataFlow
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.Application.hideKeyboard
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.Base.ViewFlipper.BaseViewFlipper
import timber.log.Timber


abstract class BaseFlow: View(currentActivity){
    //modules in flow
    val stack = mutableListOf<Int>()

    //UserData for correct routing
    var settingsCurrentFlow: DataFlow = DataFlow()
    var previousFlow: BaseFlow? = null

    var onStart: (() -> Unit)? = null
    var onFinish: (() -> Unit)? = null

    var colorStatusBar = Color.TRANSPARENT

    //value for running the first module in flow (for navigation flows)
    var isStarted = false

    val viewFlipperModule by lazy {
        val flipper = BaseViewFlipper()
        flipper.setInAnimation(this.context, android.R.anim.fade_in)
        flipper.setOutAnimation(this.context, android.R.anim.fade_out)
        flipper
    }

    init {

        onStart?.invoke()

    }

    fun onStarted(){
        currentFlow = this
        isStarted = true
    }

    fun push(module: BaseModule?) {
        module?.let { module ->
            includeModule(module)
            //correct image module
            viewFlipperModule.displayedChild = stack.indexOf(module.id)
            setBottomNavigationVisible(module.initModuleUI.isBottomNavigationVisible)
        }
    }

    fun pop() {
        if (stack.size > 1) {
            val currentModule = viewFlipperModule.currentView as BaseModule
            deInitModule(currentModule)

            val isBottomBarVisible = (viewFlipperModule.currentView as BaseModule).initModuleUI.isBottomNavigationVisible
            setBottomNavigationVisible(isBottomBarVisible)
            settingsCurrentFlow.isBottomNavigationVisible = isBottomBarVisible

            currentActivity?.hideKeyboard()
        }
    }

    fun popToRoot() {

    }

    fun deInitModule(module: BaseModule?) {
        module?.let{ module ->
            
            if (stack.contains(module.id)) {
                stack.remove(module.id)
            }
            
            if (viewFlipperModule.contains(module)) {
                //image previous module
                viewFlipperModule.displayedChild = stack.size - 1//.last()

                viewFlipperModule.removeView(module)
            }
            module.onDeInit?.invoke()
            Timber.i("Module DeInit ${module.className()}")
        }
    }

    fun includeModule(module: BaseModule?) {
        module?.let { module ->
            if (!stack.contains(module.id)){
                stack.add(module.id)
                viewFlipperModule.addView(module)
            }
        }
    }

    abstract fun start()
}