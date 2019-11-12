package com.ageone.alarm.Application.Service


import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ageone.alarm.Application.*
import com.ageone.alarm.Application.Coordinator.Flow.runFlowAuth
import com.ageone.alarm.External.Base.Activity.BaseActivity
import com.ageone.alarm.External.HTTP.API.Routes
import com.ageone.alarm.Models.User.user
import com.ageone.alarm.R
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.net.URISyntaxException

class AlarmService : Service() {

    enum class Actions {
        START,
        STOP
    }

    lateinit var socket: Socket

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("onStartCommand")
        if (intent != null) {
            val action = intent.action
            Timber.i("Intent with action $action")
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
            }
        } else {
            Timber.i("Null intent")
        }

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("Service destroyed")
    }

    private fun startService() {
        if (isServiceStarted) return
        Timber.i("Starting the service")
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        initSocket()

        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmService::lock").apply {
                    acquire()
                }
            }

        GlobalScope.launch(Dispatchers.IO) {
            while (isServiceStarted) {
                launch(Dispatchers.IO) {
                    socket.on("alert",onAlert)
                }
                delay(5000)
            }
        }
    }

    private fun stopService() {
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            Timber.i("Service stopped : ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    fun initSocket(){
        try{
            var opts = IO.Options()
            opts.reconnection = true
            socket = IO.socket("http://176.119.157.149:80")
        } catch (e: URISyntaxException){
            throw RuntimeException(e)
        }

        if(!socket.connected()) {
            socket.connect()
            Timber.i("Socket in service : ${socket.connected()}")
        }
        socket.on(Socket.EVENT_CONNECT,onConnect)
        socket.on(Socket.EVENT_RECONNECT,onReconnect)
        socket.on(Socket.EVENT_DISCONNECT,onConnectError)
        socket.on("alert",onAlert)
    }

    private var onConnect = Emitter.Listener {

        val body = JSONObject()
        body.put("token", utils.variable.token)
        socket.emit("registration", body)

        Timber.i("Socket in service connected : ${socket.connected()}")
    }

    private var onConnectError = Emitter.Listener {
        Timber.i("Socket in service error : ${it[0]}")
        GlobalScope.launch (Dispatchers.Main){
            Toast.makeText(currentActivity?.applicationContext, it[0].toString(), Toast.LENGTH_LONG).show()
        }
    }

    private var onReconnect = Emitter.Listener {
        Timber.i("Socket in service reconnect : ${it[0]}")
        GlobalScope.launch (Dispatchers.Main){
            Toast.makeText(currentActivity?.applicationContext, it[0].toString(), Toast.LENGTH_LONG).show()
        }
    }

    private var onAlert = Emitter.Listener {

        var json = it[0] as JSONObject
        Timber.i("Socket in service alert : $json")

        rxData.phoneNumber = json.optString("phone","")
        rxData.userName = json.optString("name","")
        rxData.alarmInfo = json.optString("info","")

        intent = Intent("android.intent.category.LAUNCHER")
        intent.setClassName("com.ageone.alarm", "com.ageone.alarm.Application.AppActivity")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        GlobalScope.launch (Dispatchers.Main){
            user.isAuthorized = true
            router.layout.removeAllViewsInLayout()
            currentActivity?.startActivity(intent)
            coordinator.runFlowAuth()
        }
    }

}
