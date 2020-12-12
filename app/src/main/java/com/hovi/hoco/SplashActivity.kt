package com.hovi.hoco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val username = SharePreferenceUtils.getString(this, LoginActivity.CURRENT_USERNAME)
        if (!TextUtils.isEmpty(username)) {
            startActivity(Intent(this, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        } else {
            startActivity(Intent(this, PreLoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }
}