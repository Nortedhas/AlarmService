package com.ageone.alarm.External

import android.graphics.Color
import android.view.View
import com.ageone.alarm.Application.Coordinator.Flow.isBottomNavigationExist

data class InitModuleUI (
    var isBottomNavigationVisible: Boolean = isBottomNavigationExist,
    var isBackPressed: Boolean = false,

    var isToolbarHidden: Boolean = false,
    var colorToolbar: Int = Color.TRANSPARENT,

    var firstIcon: Icon? = null,
    var secondIcon: Icon? = null,
    var thirdIcon: Icon? = null,

    var text: String? = null,
    var textListener: ((View) -> Unit)? = null
)

data class Icon (
    var icon: Int? = null,
    var size: Int = 25,
    var listener: ((View) -> Unit)? = null
)