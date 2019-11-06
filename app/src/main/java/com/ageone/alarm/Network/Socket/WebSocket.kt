package com.ageone.alarm.Network.Socket

import android.content.Intent
import com.ageone.alarm.Application.*
import com.ageone.alarm.Application.Coordinator.Flow.runFlowAuth
import com.ageone.alarm.Models.User.user
import com.example.ageone.Modules.Android6.Android6View
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

class WebSocket {

    lateinit var socket: Socket

    fun initialize() {
        try {
            socket = IO.socket("http://176.119.157.149:80")
            socket.connect()

            val body = JSONObject()
            body.put("token", utils.variable.token)
            socket.emit("registration", body)

            subscribeAlarm()
        } catch (e: Exception) {
            Timber.e("Socket connect error: ${e.message}")
        }
    }

    private fun subscribeAlarm() {
        webSocket.socket.on("alert") { message ->
            Timber.i("message : ${message[0]}")
            Timber.i("Pay succes!")

            intent = Intent("android.intent.category.LAUNCHER")
            intent.setClassName("com.ageone.alarm", "com.ageone.alarm.Application.AppActivity")
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
           //currentActivity?.
            GlobalScope.launch(Dispatchers.Main) {
                user.isAuthorized = true
                router.layout.removeAllViewsInLayout()
                currentActivity?.startActivity(intent)
                coordinator.runFlowAuth()
            }
        }
    }
}