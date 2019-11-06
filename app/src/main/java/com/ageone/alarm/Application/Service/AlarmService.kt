package com.ageone.alarm.Application.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ageone.alarm.Application.webSocket

class AlarmService : Service(){
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        webSocket.initialize()
        return Service.START_STICKY
    }
}