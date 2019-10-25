package com.ageone.alarm.Internal.Utilities

import com.ageone.alarm.External.Utils.Tools
import com.ageone.alarm.External.Utils.Variable
import com.ageone.alarm.SCAG.ConfigDefault
import com.ageone.alarm.SCAG.RealmUtilities

class Utils {
    val tools = Tools
    val variable = Variable
    var isNetworkReachable = false
    var realm = RealmUtilities
    val config = ConfigDefault
}