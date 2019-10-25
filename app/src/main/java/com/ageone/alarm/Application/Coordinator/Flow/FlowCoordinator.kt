package com.ageone.alarm.Application.Coordinator.Flow

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import com.ageone.alarm.Application.AppActivity
import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator.ViewFlipperFlowObject.viewFlipperFlow
import com.ageone.alarm.Application.Coordinator.Router.TabBar.TabBar.bottomNavigation
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.Application.router
import com.ageone.alarm.Application.setStatusBarColor
import com.ageone.alarm.External.Base.ConstraintLayout.BaseConstraintLayout
import com.ageone.alarm.External.Base.Flow.BaseFlow
import com.ageone.alarm.External.Base.Module.BaseModule
import com.ageone.alarm.External.Base.ViewFlipper.BaseViewFlipper
import com.ageone.alarm.External.InitModuleUI
import com.ageone.alarm.Models.User.user
import com.ageone.alarm.R
import nl.komponents.kovenant.Kovenant.context
import timber.log.Timber
import yummypets.com.stevia.*

var isBottomNavigationExist = true

class FlowCoordinator {
    fun setLaunchScreen() {

        router.initialize()
        renderUI()

        val launch = object: BaseModule(InitModuleUI(colorToolbar = Color.TRANSPARENT)){
        }
        launch.setBackgroundColor(Color.TRANSPARENT)

        viewFlipperFlow.subviews(
            launch
        )

        launch.toolbar
            .height(0)

    }

    fun start() {
        viewFlipperFlow.removeAllViews()
        when (LaunchInstructor.configure()) {
            LaunchInstructor.Main -> {
                Timber.i("Bottom run flow loading")
                runFlowLoading()
            }
            LaunchInstructor.Auth -> {
                runFlowAuth()
            }
        }
    }

    private fun renderUI() {
//        viewFlipperFlow = BaseViewFlipper()

        router.layout.removeAllViews()
        router.layout.subviews(
            viewFlipperFlow,
            bottomNavigation,
            blockConstraint
        )

        bottomNavigation.constrainBottomToBottomOf(router.layout)

        viewFlipperFlow
            .fillVertically()
            .fillHorizontally()
            .constrainBottomToTopOf(bottomNavigation)

        blockConstraint
            .fillVertically()
            .fillHorizontally()

        blockConstraint.removeAllViews()
        blockConstraint.subviews(
            circularProgress
        )

        circularProgress.centerInParent()

        blockConstraint.visibility = View.GONE
        circularProgress.visibility = View.GONE
    }

    val blockConstraint by lazy {
        val constraint = BaseConstraintLayout()
        constraint.setBackgroundColor(Color.argb(180, 0,0,0))
        constraint
    }

    val circularProgress by lazy {
        val circular = ProgressBar(currentActivity as Context)
        circular
    }

    object ViewFlipperFlowObject{
        var currentFlow: BaseFlow? = null
        val viewFlipperFlow: BaseViewFlipper by lazy {
            val flipper = BaseViewFlipper()

            val imgAnimationIn = AnimationUtils.loadAnimation(currentActivity, android.R.anim.fade_in)
            imgAnimationIn.duration = 200
            flipper.inAnimation = imgAnimationIn

            val imgAnimationOut = AnimationUtils.loadAnimation(currentActivity, android.R.anim.fade_out)
            imgAnimationOut.duration = 200
            flipper.outAnimation = imgAnimationOut
            flipper
        }
    }
}

fun setBottomNavigationVisible(visible: Boolean) = if (visible) {
    bottomNavigation.visibility = View.VISIBLE

} else {
    bottomNavigation.visibility = View.GONE

}

fun setStatusBarColor(color: Int) {
    currentActivity?.setStatusBarColor(color)
}

private enum class LaunchInstructor {

    Main, Auth;

    companion object {

        fun configure(isAutorized: Boolean = user.isAuthorized): LaunchInstructor {
            return when (isAutorized) {
                true -> Main
                false -> Auth
            }

        }

    }
}

