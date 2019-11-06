package com.ageone.alarm.Application.Service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.Application.intent
import com.ageone.alarm.Application.webSocket
import timber.log.Timber
import java.util.*

class AlarmService : Service(){

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("InvalidWakeLockTag")
    override fun onCreate() {
        super.onCreate()
        var pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        var wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"systemService")
        wakeLock.acquire()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        webSocket.initialize()
        return START_REDELIVER_INTENT
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        intent = Intent(currentActivity?.applicationContext,AlarmService::class.java)
        intent.setPackage(packageName)
        var restartPendingIntent = PendingIntent.getService(currentActivity?.applicationContext,1,
            intent, PendingIntent.FLAG_ONE_SHOT)

        var myAlarmManager = currentActivity?.applicationContext?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        myAlarmManager.set(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime()+1000,
            restartPendingIntent
        )

        super.onTaskRemoved(rootIntent)
    }
}
