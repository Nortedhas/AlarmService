package com.ageone.alarm.Application.Service


import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import com.ageone.alarm.Application.*
import com.ageone.alarm.Application.Coordinator.Flow.runFlowAuth
import com.ageone.alarm.External.HTTP.API.API
import com.ageone.alarm.External.HTTP.API.Routes
import com.ageone.alarm.External.HTTP.API.uuid
import com.ageone.alarm.Models.User.user
import com.ageone.alarm.SCAG.userData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.util.*

class AlarmService : Service() {

    lateinit var mySocket: Socket

    private val path = "http://45.132.18.181"

    var isConnect = false

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Timber.i("StartService")
        //start in thread for infinite work
        val thread = Thread(Runnable {

            if(utils.variable.token.isNullOrBlank()) {
                handshake {
                    initialize()
                    subscribeAlarm()
                }
            } else {
                initialize()
                subscribeAlarm()
            }
        })

        thread.start()

        return START_STICKY
    }

    //for connect to server we need token
    fun handshake(completion: () -> Unit) {
        Fuel.post("$path/handshake")
            .jsonBody(
                API().createBody(
                    mapOf(
                        "deviceId" to Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
                    )
                ).toString()
            )
            .responseString { request, response, result ->
                Timber.i("API Handshake: $request $response")

                val jsonObject = JSONObject(result.get())
                utils.variable.token = jsonObject.optString("Token")
                Timber.i("API new token: ${utils.variable.token}")

                completion.invoke()
            }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.i("Service created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("Service destroyed")
    }

    //start socket
    fun initialize() {
        val options = IO.Options()
        options.forceNew = true
        options.reconnection = true
        if (isConnect) {
            return
        } else {
            try {
                mySocket = IO.socket("$path:80", options)
                mySocket.connect()

                //onConnect listener
                mySocket.on(Socket.EVENT_CONNECT) {
                    Timber.i("Socket connected :${mySocket.connected()}")

                    val body = JSONObject()
                    body.put("token", utils.variable.token)
                    mySocket.emit("registration", body)
                    isConnect = true
                }
                //on Disconnect listener
                mySocket.on(Socket.EVENT_DISCONNECT) {
                    Timber.i("Socket connected :${mySocket.connected()}")
                    isConnect = false
                }
            } catch (e: Exception) {
                Timber.e("Socket connect error: ${e.message}")
            }
        }
    }

    //listener for emit from server
    private fun subscribeAlarm() {
        mySocket.on("onNewMessage") { message ->

            var json = message[0] as JSONObject

            rxData.phoneNumber = json.optString("phone", "")
            rxData.userName = json.optString("name", "")
            rxData.alarmInfo = json.optString("text", "")

            Timber.i("message : ${message[0]}")

            //need new intent, because intent in Application is null
            intent = Intent(this, AppActivity::class.java)
            intent.setClassName("com.ageone.alarm", "com.ageone.alarm.Application.AppActivity")
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            //start activity in UI thread. Initialize view on in Main thread
            GlobalScope.launch(Dispatchers.Main) {
                user.isAuthorized = true
                startActivity(intent)
                intent = Intent(this@AlarmService, MusicService::class.java)
                stopService(intent)
                Handler().postDelayed({
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent)
                    } else {
                        startService(intent)
                    }
                },1000)
            }
        }
    }
}
