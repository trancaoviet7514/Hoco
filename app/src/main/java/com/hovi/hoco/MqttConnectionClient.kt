package com.hovi.hoco

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.UnsupportedEncodingException

/**
 * Created by VietTC on 23/11/2020.
 */
class MqttConnectionClient {

    private var client: MqttAndroidClient? = null
    private val listCallback: ArrayList<MqttCallback> = arrayListOf()

    fun connect(context: Context, listener: IMqttActionListener?) {
        //todo get host and port from setting
        connect(context, "tcp://plcshop.webhop.me", "1883", listener)
    }

    fun connect(context: Context, host: String, port: String, listener: IMqttActionListener?) {
        val clientId = MqttClient.generateClientId()
        client = MqttAndroidClient(context, "$host:$port", clientId)

        client!!.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("mqtt", " messageArrived for topic $topic ; message: ${message.toString()}")
                for (callback: MqttCallback in listCallback) {
                    callback.messageArrived(topic, message)
                }
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("mqtt connectionLost: ", cause.toString() )
                for (callback: MqttCallback in listCallback) {
                    callback.connectionLost(cause)
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("mqtt deliveryComplete: ",token.toString())
                for (callback: MqttCallback in listCallback) {
                    callback.deliveryComplete(token)
                }
            }
        })

        try {
            val mqttConnectOptions = MqttConnectOptions()
            mqttConnectOptions.isCleanSession = false

            client!!.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.d("mqtt: ", "onSuccess")
                    listener?.onSuccess(asyncActionToken)
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("mqtt", "onFailure")
                    listener?.onFailure(asyncActionToken, exception)
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        if (client != null && client!!.isConnected) {
            client!!.disconnect()
            client = null
        }
    }

    fun isConnected(): Boolean {
        return client != null && client!!.isConnected
    }

    fun publish(topic: String, content: String, callback: IMqttActionListener?) {
        if (client != null && client!!.isConnected) {
            val encodedPayload: ByteArray
            try {
                encodedPayload = content.toByteArray(charset("UTF-8"))
                val message = MqttMessage(encodedPayload)

                client!!.publish(topic, message, null, callback)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                callback?.onFailure(null, e)
            } catch (e: MqttException) {
                e.printStackTrace()
                callback?.onFailure(null, e)
            }
        } else {
            callback?.onFailure(null, null)
        }
    }

    fun subscribe(topic: String, callback: SubscribeTopicCallback?) {
        if (client != null && client!!.isConnected) {
            try {
                addCallback(callback)
                client!!.subscribe(topic, 1, null, callback)
            } catch (e: MqttException) {
                e.printStackTrace()
                callback?.onFailure(null, e)
            }
        } else {
            callback?.onFailure(null, null)
        }

    }

    fun addCallback(callback: MqttCallback?) {
        if (callback != null) {
            listCallback.add(callback)
        }
    }

    fun removeCallback(callback: MqttCallback) {
        listCallback.remove(callback)
    }

    companion object {
        var INSTANCE: MqttConnectionClient? = null
        fun getInstance(): MqttConnectionClient {
            if (INSTANCE == null) {
                INSTANCE = MqttConnectionClient()
            }
            return INSTANCE!!
        }
    }

    interface SubscribeTopicCallback : IMqttActionListener, MqttCallback {
    }
}