package com.ageone.alarm.Network.Socket

import com.ageone.alarm.Application.utils
import io.socket.client.Manager
import io.socket.client.Socket
import io.socket.engineio.client.Transport
import org.json.JSONObject
import timber.log.Timber

class WebSocket {

    lateinit var socket: Socket

    fun initialize() {
        try {
//            socket = IO.socket("${DataBase.url}:80")
            socket.connect()
            val body = JSONObject()
            body.put("token", utils.variable.token)
            socket.emit("registration", body)
        } catch (e: Exception) {
            Timber.e("Socket connect error: ${e.message}")
        }
    }

    fun connect() {


        socket.io().on(Manager.EVENT_TRANSPORT) { args ->
            val transport = args[0] as Transport

            transport.on(Transport.EVENT_REQUEST_HEADERS) { args ->
                val headers = args[0] as MutableMap<String, List<String>>
                Timber.i("set access UserHandshake")
                // modify request headers
                headers["x-access-UserHandshake"] = listOf(utils.variable.token)
            }

            transport.on(Transport.EVENT_RESPONSE_HEADERS) { args ->
                val headers = args[0] as Map<String, List<String>>
                // access response headers
                val cookie = headers["Set-Cookie"]?.get(0)
            }

        }

        socket.connect()
            .on("orderCheck") {
                Timber.i("Socket Order check")
            }

        socket.connect()
            .on(Socket.EVENT_CONNECT) {
                Timber.i("connected")
            }
            .on(Socket.EVENT_DISCONNECT) { println("disconnected") }


    }
}
