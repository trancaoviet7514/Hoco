package com.hovi.hoco.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.User


/**
 * Created by VietTC on 05/12/2020.
 */
class FireBaseDataBaseUtils {

    companion object {

        fun signUp(userName: String, password: String, connectionString: String, callback: CallBack) {
            val ref = Firebase.database.getReference(userName)

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    ref.removeEventListener(this)
                    val value = dataSnapshot.value
                    if (value == null) {
                        ref.child("password").setValue(password)
                        ref.child("connectionString").setValue(connectionString)
                        callback.onSuccess(null)
                    } else {
                        callback.onFail()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onFail()
                }
            })

        }

        fun login(userName: String, password: String, callback: CallBack) {
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

        fun updateConnectionString(userName: String, newConnectionString: String, callback: CallBack) {
            val ref = Firebase.database.getReference("$userName/connectionString")
            val task = ref.setValue(newConnectionString)

            task.addOnSuccessListener {
                callback.onSuccess(null)
            }

            task.addOnFailureListener {
                callback.onFail()
            }
        }

        fun getListAccount2Shared(userName: String, callback:CallBack) {
            val ref = Firebase.database.getReference("$userName/listGmail2Share")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value
                    if (value != null && value is HashMap<*, *>) {
                        callback.onSuccess(value.keys)
                    } else if (value == null) {
                        callback.onSuccess(HashMap<String,String>().keys)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback.onFail()
                }
            })
        }

        fun addNewAccount2Share(userName: String, gmail2Share: String, callback: CallBack?) {
            val ref = Firebase.database.getReference("$userName/listGmail2Share/$gmail2Share")
            val ref2 = Firebase.database.getReference("$gmail2Share/connectionString")

            val task = ref.setValue("shared")
            val task2 = ref2.setValue(GlobalData.currentUser!!.connectionString)

            callback?.onSuccess(null)
        }

        fun removeAccount2Share(userName: String, gmail2Share: String, callback: CallBack?) {
            val ref = Firebase.database.getReference("$userName/listGmail2Share/$gmail2Share")
            val ref2 = Firebase.database.getReference("$gmail2Share/connectionString")

            val task = ref.removeValue()
            val task2 = ref2.removeValue()

            callback?.onSuccess(null)
        }

        fun getConnectionString(userName: String, callback: CallBack?) {
            val ref = Firebase.database.getReference("$userName/connectionString")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.value
                    callback?.onSuccess(value)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback?.onFail()
                }
            })
        }
    }

    interface CallBack {
        fun onSuccess(any: Any?)
        fun onFail()
    }
}