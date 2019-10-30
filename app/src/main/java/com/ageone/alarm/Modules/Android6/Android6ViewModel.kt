package com.example.ageone.Modules.Android6

import com.ageone.alarm.External.Interfaces.InterfaceModel
import com.ageone.alarm.External.Interfaces.InterfaceViewModel

class Android6ViewModel : InterfaceViewModel {
    var model = Android6Model()

    enum class EventType {
        OnClickButtonAndroid6

    }

    /*var realmData = listOf<>()
    fun loadRealmData() {
        realmData = utils.realm.product.getAllObjects()//TODO: change type data!
    }*/

    fun initialize(recievedModel: InterfaceModel, completion: () -> (Unit)) {
        if (recievedModel is Android6Model) {
            model = recievedModel
            completion.invoke()
        }
    }
}

class Android6Model : InterfaceModel {

}
