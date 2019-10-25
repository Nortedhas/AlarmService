package com.ageone.alarm.External.Base.SeekBar

import android.graphics.PorterDuff
import android.widget.SeekBar
import com.ageone.alarm.Application.currentActivity

class BaseSeekBar: SeekBar(currentActivity) {

    var colorProgressLine: Int? = null
    var colorThumb: Int? = null

    fun initialize() {

        colorProgressLine?.let { color ->
            progressDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }


        colorThumb?.let { color ->
            thumb.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }

    }
}