package com.hovi.hoco

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hovi.hoco.databinding.ActivityMainBinding
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttMessage


class MainActivity : AppCompatActivity() {

    lateinit var vb: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.btnConnect.setOnClickListener(View.OnClickListener {
            val listener = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("mqtt", "onSuccess")
                    vb.btnConnect.text = "Disconnect"
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("mqtt", "onFailure")
                    vb.btnConnect.text = "Connect"
                }

            }

            if (MqttConnectionClient.getInstance().isConnected()) {
                MqttConnectionClient.getInstance().disconnect()
                vb.btnConnect.text = "Connect"
            } else {
                vb.btnConnect.text = "Connecting"
                MqttConnectionClient.getInstance().connect(
                        applicationContext,
                        vb.txtHost.text.toString(), vb.txtPort.text.toString(),
                        listener)
            }
        })

         vb.btnSend.setOnClickListener(View.OnClickListener {
             val listener = object : IMqttActionListener {
                 override fun onSuccess(asyncActionToken: IMqttToken?) {
                     Log.d("mqtt", "publish onSuccess")
                     Toast.makeText(applicationContext, "publish success", Toast.LENGTH_SHORT).show()
                 }

                 override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                     Log.d("mqtt", "publish onFailure")
                     Toast.makeText(applicationContext, "publish failure", Toast.LENGTH_SHORT).show()
                 }

             }

             MqttConnectionClient.getInstance().publish(vb.txtTopic.text.toString(), vb.txtMessage.text.toString(), listener)
         })

        vb.btnSubscribe.setOnClickListener(View.OnClickListener {
            val listener = object : MqttConnectionClient.SubscribeTopicCallbackAdapter() {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("mqtt", "subscribe onSuccess")
                    Toast.makeText(applicationContext, "subscribe success", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("mqtt", "subscribe onSuccess")
                    Toast.makeText(applicationContext, "subscribe failure", Toast.LENGTH_SHORT).show()
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d("mqtt", "messageArrived topic: $topic; message: $message")
                    vb.txtMessagesSub.setText(message.toString())
                }
            }

            MqttConnectionClient.getInstance().subscribe(vb.txtSubcribeTopic.text.toString(), listener)
        })
    }

    override fun onStart() {
        super.onStart()

        val listener = object : MqttConnectionClient.SubscribeTopicCallbackAdapter() {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                super.onSuccess(asyncActionToken)
                MqttConnectionClient.getInstance().publish("STATUS", "fetch data", null)
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                super.messageArrived(topic, message)
                vb.txtMessagesSub.setText(message.toString())
            }
        }

        if (MqttConnectionClient.getInstance().isConnected()) {
            MqttConnectionClient.getInstance().subscribe("STATUS", listener)
        } else {
            val connectListener = object : MqttConnectionClient.IMqttActionListenerAdapter() {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    MqttConnectionClient.getInstance().subscribe("STATUS", listener)
                }
            }

            MqttConnectionClient.getInstance().connect(applicationContext, connectListener)
        }
    }
}