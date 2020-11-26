package com.hovi.hoco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hovi.hoco.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.llSettingItemConfigServer.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConfigServerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })

        binding.llSettingItemLogOut.setOnClickListener(View.OnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    }
                }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> run {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}