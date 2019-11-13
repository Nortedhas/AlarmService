package com.ageone.alarm.Application.Service


import android.app.*
import android.content.Intent
import android.os.IBinder
import com.ageone.alarm.Application.*
import com.ageone.alarm.Application.Coordinator.Flow.runFlowAuth
import com.ageone.alarm.External.HTTP.API.API
import com.ageone.alarm.External.HTTP.API.Routes
import com.ageone.alarm.External.HTTP.API.uuid
import com.ageone.alarm.Models.User.user
import com.ageone.alarm.SCAG.userData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import java.io.*
import java.nio.charset.Charset
import java.util.*

class AlarmService : Service() {

    lateinit var mySocket: Socket

    var isConnect = false
    var uuid = if (user.hashId.isNotBlank()) user.hashId else UUID.randomUUID().toString()
    val token = utils.variable.token

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val thread = Thread(Runnable {
            handshake {
                initialize()
                subscribeAlarm()
            }
        })

        thread.start()

        return START_STICKY
    }

    fun handshake(completion: () -> Unit){
        Fuel.post("http://176.119.157.149/handshake")
            .jsonBody(API().createBody(mapOf(
                "deviceId" to uuid
            )).toString())
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

    fun initialize() {
        val options = IO.Options()
        options.forceNew = true
        options.reconnection = true
        if(isConnect){
            return
        } else {
            try {
                mySocket = IO.socket("http://176.119.157.149:80", options)
                mySocket.connect()


                mySocket.on(Socket.EVENT_CONNECT) {
                    Timber.i("Socket connected :${mySocket.connected()}")

                    val body = JSONObject()
                    body.put("token", utils.variable.token)
                    mySocket.emit("registration", body)
                    isConnect = true
                }

                mySocket.on(Socket.EVENT_DISCONNECT) {
                    Timber.i("Socket connected :${mySocket.connected()}")
                    isConnect = false
                }
            } catch (e: Exception) {
                Timber.e("Socket connect error: ${e.message}")
            }
        }
    }

    private fun subscribeAlarm() {
        mySocket.on("alert") { message ->

            var json = message[0] as JSONObject

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
//
//    fun run(host: String, port: Int){
//        try{
//            val socket = Socket(host, port)
//            val input = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//            val message = "alert"
//            val out = socket.getOutputStream()
//            val print = PrintWriter(out)
//            print.flush()
//
//            while(true) {
//                out.write((message + '\n').toByteArray(Charset.defaultCharset()))
//                out.flush()
//                    var result = input.readLine() ?: ""
//                    Timber.i("Result : $result")
//            }
//        }catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun write(message: String) {
//    }
}
