package com.hovi.hoco.utils

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hovi.hoco.model.User
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.*
import java.util.Base64.getEncoder
import kotlin.collections.HashMap

/**
 * Created by VietTC on 05/12/2020.
 */
class FireBaseDataBaseUtils {

    companion object {
        fun writeDemo() {
            val database = Firebase.database
            val myRef = database.getReference("message/bbc/hi")
myRef.setValue("abc")
//            myRef.child("haha").setValue("Hello, World!")

        }

        fun signUp(userName: String, password: String, connectionString: String, callback: SignUpCallBack) {
            val ref = Firebase.database.getReference(userName)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    ref.removeEventListener(this)
                    val value = dataSnapshot.value
                    if (value == null) {
                        ref.child("password").setValue(password)
                        ref.child("connectionString").setValue(connectionString)
                        callback.onSuccess()
                    } else {
                        callback.onFail()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onFail()
                }
            })

        }

        fun login(userName: String, password: String, callback: SignInCallBack) {
            val ref = Firebase.database.getReference(userName)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    ref.removeEventListener(this)
                    val value = dataSnapshot.value
                    if (value != null && value is HashMap<*, *>) {
                        if (value["password"] == password) {
                            val user = User(userName, value["password"].toString(), value["connectionString"].toString())
                            callback.onSuccess(user)
                        } else {
                            callback.onFail()
                        }
                    } else {
                        callback.onFail()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onFail()
                }
            })

        }

        fun changePassword(userName: String, password: String, callback: CallBack) {
            val ref = Firebase.database.getReference("$userName/password")
            ref.setValue(password)
            callback.onSuccess(null)
        }
    }

    interface CallBack {
        fun onSuccess(any: Any?)
        fun onFail()
    }

    interface SignUpCallBack {
        fun onSuccess()
        fun onFail()
    }

    interface SignInCallBack {
        fun onSuccess(user: User)
        fun onFail()
    }
}