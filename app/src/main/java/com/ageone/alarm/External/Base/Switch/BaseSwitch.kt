package com.ageone.alarm.External.Base.Switch

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.ageone.alarm.Application.currentActivity

class BaseSwitch: SwitchCompat(currentActivity) {
    var states =
        arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))

    var thumbColors: IntArray? = null

    var trackColors: IntArray? = null

    fun initialize() {
        thumbColors?.let { thumbColors ->
            DrawableCompat.setTintList(
                DrawableCompat.wrap(thumbDrawable),
                ColorStateList(states, thumbColors)
            )
        }

        trackColors?.let { trackColors ->
            DrawableCompat.setTintList(
                DrawableCompat.wrap(trackDrawable),
                ColorStateList(states, trackColors)
            )
        }
    }
}