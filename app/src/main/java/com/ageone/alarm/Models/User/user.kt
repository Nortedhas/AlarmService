package com.ageone.alarm.Models.User

import com.ageone.alarm.SCAG.UserData
import net.alexandroid.shpref.ShPref

object user {

    var hashId: String
        get() = ShPref.getString("userHashId", "")
        set(value) = ShPref.put("userHashId", value)

    var fcmToken: String
        get() = ShPref.getString("userFcmToken", "")
        set(value) = ShPref.put("userFcmToken", value)

    var isAuthorized: Boolean
        get() = ShPref.getBoolean("userIsAuthorized", false)
        set(value) = ShPref.put("userIsAuthorized", value)

    var data = UserData
}