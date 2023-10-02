package com.theroyalsoft.telefarmer.sockethelper

import android.os.Handler
import android.os.Looper
import com.theroyalsoft.telefarmer.utils.DeviceIDUtil
import com.theroyalsoft.telefarmer.utils.LocalData
import io.socket.client.IO
import io.socket.client.Socket
import timber.log.Timber
import java.net.URISyntaxException

/**
 * Created by Pritom Dutta on 6/9/23.
 */
object SocketHandler {

    lateinit var mSocket: Socket


    @Synchronized
    fun setSocket(socketUrl: String, userID: String) {
        try {
// "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
// "http://localhost:3000/" will not work
// If you want to use your physical phone you could use your ip address plus :3000
// This will allow your Android Emulator and physical device at your home to connect to the server

            val customId = "uuid=${userID}"
            val devicesId = "deviceId=" + DeviceIDUtil.getDeviceID()
            val query = "$customId&$devicesId"

            Timber.e("Socket:$query")

            val opts = IO.Options()
            opts.forceNew = false
            opts.query = query
            mSocket = IO.socket(socketUrl, opts)
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }

    @Synchronized
    fun sendData(eventName: String, data: String) {
        mSocket.emit(eventName, data)
    }
}