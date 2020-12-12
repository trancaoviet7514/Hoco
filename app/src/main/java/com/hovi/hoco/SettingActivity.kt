package com.hovi.hoco

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hovi.hoco.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    lateinit var vb: ActivitySettingBinding
    var demoActivityTrigger = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.txtUserName.text = SharePreferenceUtils.getString(this, LoginActivity.CURRENT_USERNAME)

        vb.itemConnect.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ConfigServerActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })

        vb.itemClockApp.setOnClickListener(View.OnClickListener {  })
        vb.itemDeviceSetting.setOnClickListener(View.OnClickListener {  })
        vb.itemHelp.setOnClickListener(View.OnClickListener {  })

        vb.itemSignOut.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(getString(com.hovi.hoco.R.string.str_confirm_log_out))
                    .setPositiveButton(getString(com.hovi.hoco.R.string.str_yes)) { _, _ ->
                        SharePreferenceUtils.setString(this, LoginActivity.CURRENT_USERNAME, "")
                        startActivity(Intent(this, PreLoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
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