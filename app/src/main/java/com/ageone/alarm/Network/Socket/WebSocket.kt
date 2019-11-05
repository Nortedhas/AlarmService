package com.ageone.alarm.Network.Socket

import com.ageone.alarm.Application.utils
import com.ageone.alarm.Application.webSocket
import io.socket.client.IO
import io.socket.client.Socket
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
            Timber.i("Pay succes!")


        }
    }
}
