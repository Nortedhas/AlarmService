package com.ageone.alarm.Application.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.ageone.alarm.Application.currentActivity
import com.ageone.alarm.R

class MusicService : Service(){
    var mediaPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer.create(currentActivity?.applicationContext, R.raw.soad)
        mediaPlayer?.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
    }
}