package com.example.ageone.Modules.LoadingScreen

import com.ageone.alarm.External.Interfaces.InterfaceModel
import com.ageone.alarm.External.Interfaces.InterfaceViewModel


class LoadingScreenViewModel : InterfaceViewModel {
    var model = LoadingScreenModel()

    fun initialize(recievedModel: InterfaceModel, completion: () -> (Unit)) {
        if (recievedModel is LoadingScreenModel) {
            model = recievedModel
            completion.invoke()
        }
    }

    enum class EventType {
        OnTimerStop
    }
}

class LoadingScreenModel : InterfaceModel {

}