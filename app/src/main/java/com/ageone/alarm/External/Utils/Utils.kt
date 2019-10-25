package com.ageone.alarm.External.Utils

import android.graphics.Color
import com.ageone.alarm.Application.utils

object Tools {
    fun hex(hex: String): Int {
        return Color.parseColor("#$hex")
    }

    fun getClassName(name: String): String {
        return name.split("{")[0]
    }

    fun getActualSizeFromDes(size: Int) = utils.variable.displayWidth * (size / utils.variable.widthDisplayDesign)
}

object Variable {
    var statusBarHeight = 0
    var displayWidth = 0
    var displayHeight = 0
    var actionBarHeight = 0
    var token = ""
    var vkSdkTokenUser = ""

    val widthDisplayDesign = 375F
}