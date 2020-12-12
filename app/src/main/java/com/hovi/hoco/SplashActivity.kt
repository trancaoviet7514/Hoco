package com.hovi.hoco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.User

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val username = SharePreferenceUtils.getString(this, LoginActivity.CURRENT_USERNAME)
        if (!TextUtils.isEmpty(username)) {
            val password = SharePreferenceUtils.getString(this, LoginActivity.CURRENT_PASSWORD)
            val connectionString = SharePreferenceUtils.getString(this, LoginActivity.CURRENT_CONNECTION_STRING)

            GlobalData.currentUser = User(username!!, password!!, connectionString!!)

            startActivity(Intent(this, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        } else {
            startActivity(Intent(this, PreLoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }
}