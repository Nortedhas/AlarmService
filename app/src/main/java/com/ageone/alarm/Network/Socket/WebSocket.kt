package com.ageone.alarm.Network.Socket

import android.content.Intent
import com.ageone.alarm.Application.*
import com.ageone.alarm.Application.Coordinator.Flow.runFlowAuth
import com.ageone.alarm.Models.User.user
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.util.*

class WebSocket {

    lateinit var socket: Socket


    fun initialize() {
        try {
            socket = IO.socket("http://176.119.157.149:80")
            socket.connect()

            val body = JSONObject()
            body.put("token", utils.variable.token)
            socket.emit("registration", body)
            Timber.i("Websocket initialize")
            subscribeAlarm()
        } catch (e: Exception) {
            Timber.e("Socket connect error: ${e.message}")
        }
    }
}

private fun subscribeAlarm() {
    webSocket.socket.on("alert") { message ->

        val json = message[0] as JSONObject

        rxData.phoneNumber = json.optString("phone","")
        rxData.userName = json.optString("name","")
        rxData.alarmInfo = json.optString("info","")

        Timber.i("message : ${message[0]}")

        intent = Intent("android.intent.category.LAUNCHER")
        intent.setClassName("com.ageone.alarm", "com.ageone.alarm.Application.AppActivity")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        GlobalScope.launch(Dispatchers.Main) {
            user.isAuthorized = true
            router.layout.removeAllViewsInLayout()
            currentActivity?.startActivity(intent)
            coordinator.runFlowAuth()
        }
    }
}