package com.hovi.hoco

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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


        binding.itemConnect.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConfigServerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })

        binding.itemInfo.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AppInfoActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })

        binding.itemSignOut.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(getString(com.hovi.hoco.R.string.str_confirm_log_out))
                    .setPositiveButton(getString(com.hovi.hoco.R.string.str_yes)) { _, _ ->
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

                        val googleSignInClient = GoogleSignIn.getClient(this, gso)
                        googleSignInClient.signOut()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        FirebaseAuth.getInstance().signOut()
                                        startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                                    }
                                }
                    }
                    .setNegativeButton(getString(com.hovi.hoco.R.string.str_no), null)
                    .show()

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> run {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}