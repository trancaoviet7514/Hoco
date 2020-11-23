package com.hovi.hoco

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hovi.hoco.databinding.ActivityMainBinding
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.UnsupportedEncodingException


class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding
    var client: MqttAndroidClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.btnConnect.setOnClickListener(View.OnClickListener {
            if (client != null && client!!.isConnected) {
                client!!.disconnect()
                client = null
                viewBinding.btnConnect.text = "Connect"
                return@OnClickListener
            }

            viewBinding.btnConnect.text = "Connecting..."
            val serverUri = viewBinding.txtHost.text.toString() + ":" + viewBinding.txtPort.text.toString()

            val clientId = MqttClient.generateClientId()
            client = MqttAndroidClient(this.applicationContext, serverUri, clientId)

            client!!.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d("mqtt", " messageArrived for topic $topic ; message: ${message.toString()}")
                    if (topic == viewBinding.txtSubcribeTopic.text.toString()) {
                        viewBinding.txtMessagesSub.setText(message.toString())
                    }
                }

                override fun connectionLost(cause: Throwable?) {
                    Log.d("mqtt connectionLost: ", cause.toString() )
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d("mqtt deliveryComplete: ",token.toString())
                }
            })

            try {
                val mqttConnectOptions = MqttConnectOptions()
                mqttConnectOptions.isCleanSession = false

               client!!.connect(mqttConnectOptions, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        Log.d("mqtt: ", "onSuccess")
                        Toast.makeText(this@MainActivity, "Connected", Toast.LENGTH_SHORT).show()
                        viewBinding.btnConnect.text = "Disconnect"
                    }

                    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                        // Something went wrong e.g. connection timeout or firewall problems
                        Log.d("mqtt", "onFailure")
                        Toast.makeText(this@MainActivity, "Connect Fail", Toast.LENGTH_SHORT).show()
                        viewBinding.btnConnect.text = "Connect"
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        })

         viewBinding.btnSend.setOnClickListener(View.OnClickListener {
             publish(viewBinding.txtTopic.text.toString(), viewBinding.txtMessage.text.toString())
         })

        viewBinding.btnSubscribe.setOnClickListener(View.OnClickListener {
            subscribe(viewBinding.txtSubcribeTopic.text.toString())
        })
    }

    fun publish(topic: String, content: String) {
        if (client != null && client!!.isConnected) {
            var encodedPayload: ByteArray
            try {
                encodedPayload = content.toByteArray(charset("UTF-8"))
                val message = MqttMessage(encodedPayload)

                client!!.publish(topic, message)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this@MainActivity, "Check connection please!", Toast.LENGTH_SHORT).show()
        }

    }

    fun subscribe(topic: String) {
        if (client != null && client!!.isConnected) {
            val qos = 1
            try {
                val subToken = client!!.subscribe(topic, qos)
                subToken.actionCallback = object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        Log.d("mqtt subcribe success", "")
                        Toast.makeText(this@MainActivity, "Subscribe topic $topic success", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(asyncActionToken: IMqttToken,
                                           exception: Throwable) {
                        Toast.makeText(this@MainActivity, "Subscribe topic $topic fail", Toast.LENGTH_SHORT).show()
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards
                    }
                }
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this@MainActivity, "Check connection please!", Toast.LENGTH_SHORT).show()
        }

    }
}