package com.hovi.hoco.activity

import android.R
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hovi.hoco.databinding.ActivitySettingBinding
import java.util.*


class SettingActivity : AppCompatActivity() {
    lateinit var vb: ActivitySettingBinding
    var demoActivityTrigger = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentUser = FirebaseAuth.getInstance().currentUser

        vb.txtUserName.text = currentUser!!.displayName
        Glide.with(this)
            .load(currentUser.photoUrl.toString())
            .into(vb.profileImage);

        vb.itemManagingAccess.setOnClickListener {
            startActivity(Intent(this, ManagingAccessActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }

        vb.itemClockApp.setOnClickListener {
            startActivity(Intent(this, SettingPassCodeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
        vb.itemDeviceSetting.setOnClickListener(View.OnClickListener {  })

        vb.itemAppInfo.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AppInfoActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })

        vb.itemSignOut.setOnClickListener(View.OnClickListener {
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

        vb.root.setOnClickListener(View.OnClickListener {
            demoActivityTrigger++;
            if (demoActivityTrigger == 7) {
                demoActivityTrigger = 0
                startActivity(Intent(this, DemoActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
            }
        })

        vb.itemChangeLanguage.setOnClickListener{
            val contentView = layoutInflater.inflate(com.hovi.hoco.R.layout.dialog_change_language, null)

            val dialog = AlertDialog.Builder(this)
                .setView(contentView)
                .show()

            val layoutTV = contentView.findViewById<ConstraintLayout>(com.hovi.hoco.R.id.layout_tieng_viet)
            layoutTV.setOnClickListener {
                dialog.dismiss()
                changeToLanguage("vi")
            }

            val layoutEN = contentView.findViewById<ConstraintLayout>(com.hovi.hoco.R.id.layout_english)
            layoutEN.setOnClickListener {
                dialog.dismiss()
                changeToLanguage("en")
            }
        }
    }

    private fun changeToLanguage(code: String) {
        val resources: Resources = applicationContext.resources
        val overrideConfiguration = resources.configuration
        val overrideLocale = Locale(code)

        overrideConfiguration.setLocale(overrideLocale)
        applicationContext.createConfigurationContext(overrideConfiguration)

        recreate()
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