package com.ageone.alarm.External.Extensions.Application

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.ageone.alarm.Application.Service.MusicService
import com.ageone.alarm.Application.intent

class FTActivityLifecycleCallbacks: Application.ActivityLifecycleCallbacks {

    var currentActivity: Activity? = null

    override fun onActivityPaused(activity: Activity?) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
        currentActivity = activity
        currentActivity = activity
        intent = Intent(activity, MusicService::class.java)
        activity?.startService(intent)
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
        intent = Intent(activity, MusicService::class.java)
        activity?.stopService(intent)
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        currentActivity = activity
    }

}