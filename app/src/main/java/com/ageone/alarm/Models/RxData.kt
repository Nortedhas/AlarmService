package com.ageone.alarm.Models

import com.ageone.alarm.External.RxBus.RxBus
import kotlin.properties.Delegates


class RxData {

    var phoneNumber: String by Delegates.observable("") { property, oldValue, newValue ->
        if(newValue == oldValue) return@observable

        if (newValue != oldValue) {
            RxBus.publish(RxEvent.EventChangePhoneNumber(newValue))
        }
    }

    var userName: String by Delegates.observable(""){ property, oldValue, newValue ->
        if(newValue == oldValue) return@observable
        if (newValue != oldValue) {
            RxBus.publish(RxEvent.EventChangeUserName(newValue))
        }
    }

    var alarmInfo: String by Delegates.observable(""){ property, oldValue, newValue ->
        if(newValue == oldValue) return@observable
        if (newValue != oldValue) {
            RxBus.publish(RxEvent.EventChangeAlarmInfo(newValue))
        }
    }
}

class RxEvent{
    data class EventChangePhoneNumber(var phoneNumber: String)
    data class EventChangeUserName(var userName: String)
    data class EventChangeAlarmInfo(var userName: String)
}

