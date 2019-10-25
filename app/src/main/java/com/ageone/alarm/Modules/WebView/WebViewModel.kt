package com.ageone.alarm.Modules

import com.ageone.alarm.External.Interfaces.InterfaceModel
import com.ageone.alarm.External.Interfaces.InterfaceViewModel

class WebViewViewModel : InterfaceViewModel {
    var model = WebViewModel()

    fun initialize(recievedModel: InterfaceModel, completion: ()->(Unit)) {
        if (recievedModel is WebViewModel) {
            model = recievedModel
            completion.invoke()
        }
    }
    enum class EventType {

    }
}

class WebViewModel : InterfaceModel {
    var url = ""
}
