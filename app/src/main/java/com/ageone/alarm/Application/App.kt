package com.ageone.alarm.Application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ageone.alarm.Application.Coordinator.Flow.FlowCoordinator
import com.ageone.alarm.Application.Coordinator.Router.Router
import com.ageone.alarm.BuildConfig
import com.ageone.alarm.External.Base.Activity.BaseActivity
import com.ageone.alarm.External.Extensions.Application.FTActivityLifecycleCallbacks
import com.ageone.alarm.External.HTTP.API.API
import com.ageone.alarm.Internal.Utilities.Utils
import com.ageone.alarm.Models.RxData
import com.ageone.alarm.Network.Socket.WebSocket
import com.ageone.alarm.R
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import net.alexandroid.shpref.ShPref
import timber.log.Timber

val router = Router()
val coordinator = FlowCoordinator()

val utils = Utils()
val api = API()
//val database = DataBase
val rxData = RxData()
var webSocket = WebSocket()
var intent = Intent()

val currentActivity: BaseActivity?
    get() = App.instance?.mFTActivityLifecycleCallbacks?.currentActivity as BaseActivity

class App: Application()  {

    init {
        instance = this
    }

    val mFTActivityLifecycleCallbacks = FTActivityLifecycleCallbacks()

    override fun onCreate() {
        super.onCreate()

        // MARK: SharePreferences

        ShPref.init(this, ShPref.APPLY)

        // MARK: Timber
        if (BuildConfig.DEBUG) {
            val deviceManufacturer = android.os.Build.MANUFACTURER
            if (deviceManufacturer.toLowerCase().contains("huawei")) {
                Timber.plant(HuaweiTree())
            } else {
                Timber.plant(Timber.DebugTree())
            }
        }

        ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isConnectedToInternet ->
//                    Timber.i("Internet are allowed")
                    utils.isNetworkReachable = isConnectedToInternet
            }

        Realm.init(this)

        registerActivityLifecycleCallbacks(mFTActivityLifecycleCallbacks)
    }

    companion object {

        var instance: App? = null

    }
}

class HuaweiTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        var priority = priority
        if (priority == Log.VERBOSE || priority == Log.DEBUG)
            priority = Log.INFO
        super.log(priority, tag, message, t)
    }
}

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager
    private val ADMIN_CHANNEL_ID = "nahodka"

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Timber.i("Notification title : ${p0.data["title"]}")
        Timber.i("Notification title : ${p0.data}")

        createNotificationChannel()
        p0?.let{
            //val customMessage = it.data.getValue(CUSTOM_KEY)
            val title = it.notification?.title?: "Backup title"
            val message = it.notification?.body?: "Backup message"
            Timber.i("Title : $title , message : $message")
            sendNotification(title, "$message")
        }
    }

    private fun sendNotification(notificationTitle: String, notificationContent: String){
        val intent = Intent(this, currentActivity!!::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationTitle)
            .setContentText(notificationContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setChannelId(CHANNEL_ID)

        with(NotificationManagerCompat.from(this)){
            notify(12345,notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= 26) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, "nahodka channel", importance)
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor = Color.GRAY
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "firebase_playground_channel_id"
        const val CUSTOM_KEY = "custom_key"
    }

    override fun onNewToken(p0: String) {
        Timber.i("Cloud token : $p0")
    }
}

