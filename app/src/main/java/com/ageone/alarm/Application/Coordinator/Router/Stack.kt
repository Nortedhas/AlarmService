package com.ageone.alarm.Application.Coordinator.Router

import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator
import com.ageone.alarm.Application.Coordinator.Router.TabBar.Stack
import timber.log.Timber

fun FlowCoordinator.createStackFlows(startFlow: Int) {
    Stack.flows.clear()

    // MARK: in order like in navigation


    Timber.i("Bottom create stack flows")

//    runFlowMain()

    Stack.flows[startFlow].start()
}