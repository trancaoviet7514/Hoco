package com.hovi.hoco

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.databinding.ActivitySettingBinding
import com.hovi.hoco.databinding.ActivitySettingPassCodeBinding

class SettingPassCodeActivity : AppCompatActivity() {
    lateinit var vb: ActivitySettingPassCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySettingPassCodeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.layoutCreateNewPassCode.setOnClickListener {
            startActivityForResult(Intent(this, PassCodeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 0)
        }
        vb.layoutRemoveAppPassCode.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(getString(com.hovi.hoco.R.string.str_confirm_unlock_passcode))
                .setPositiveButton(getString(com.hovi.hoco.R.string.str_yes)) { _, _ ->
                    SharePreferenceUtils.setString(this, PassCodeActivity.APP_PASS_CODE, "")
                    updateUI()
                }
                .setNegativeButton(getString(com.hovi.hoco.R.string.str_no), null)
                .show()
        }

        vb.layoutChangePassCode.setOnClickListener {
            val intent = Intent(this, PassCodeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra(PassCodeActivity.TITLE_ACTIVITY, "Đổi mã khóa")
            intent.putExtra(PassCodeActivity.MESSAGE_SUCCESS, "Đổi mã khóa thành công")
            startActivityForResult(intent, 0)
        }

        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            updateUI()
        }
    }

    fun updateUI() {
        val passCode = SharePreferenceUtils.getString(this, PassCodeActivity.APP_PASS_CODE)
        if (!TextUtils.isEmpty(passCode)) {
            vb.layoutCreateNewPassCode.isEnabled = false
            vb.txtNewPassCode.isEnabled = false

            vb.layoutRemoveAppPassCode.isEnabled = true
            vb.txtRemovePassCode.isEnabled = true

            vb.layoutChangePassCode.isEnabled = true
            vb.txtChangePassCode.isEnabled = true
        } else {
            vb.layoutCreateNewPassCode.isEnabled = true
            vb.txtNewPassCode.isEnabled = true

            vb.layoutRemoveAppPassCode.isEnabled = false
            vb.txtRemovePassCode.isEnabled = false

            vb.layoutChangePassCode.isEnabled = false
            vb.txtChangePassCode.isEnabled = false
        }
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