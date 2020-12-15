package com.hovi.hoco

import android.R
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.MenuItem
import com.hanks.passcodeview.PasscodeView
import com.hovi.hoco.databinding.ActivityPassCodeBinding

class PassCodeActivity : AppCompatActivity() {

    lateinit var vb: ActivityPassCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPassCodeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val title = intent.getStringExtra(TITLE_ACTIVITY)
        if (!TextUtils.isEmpty(title)) {
            vb.toolbarMain.title = title
        }

        val messageSuccess = intent.getStringExtra(MESSAGE_SUCCESS)
        if (!TextUtils.isEmpty(messageSuccess)) {
            vb.passcodeView.correctInputTip = messageSuccess
        }

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.passcodeView.listener = object : PasscodeView.PasscodeViewListener {
            override fun onSuccess(number: String?) {
                if (!TextUtils.isEmpty(number)) {
                    SharePreferenceUtils.setString(this@PassCodeActivity, APP_PASS_CODE, number!!)

                    Handler(Looper.getMainLooper()).postDelayed({
                        setResult(Activity.RESULT_OK)
                        finish()
                    }, 700)
                }
            }

            override fun onFail(wrongNumber: String?) {

            }

        }

        val action = intent.getIntExtra(TYPE_OPEN, PasscodeView.PasscodeViewType.TYPE_SET_PASSCODE)
        if (action == PasscodeView.PasscodeViewType.TYPE_CHECK_PASSCODE) {
            val passCode = SharePreferenceUtils.getString(this, APP_PASS_CODE)
            vb.passcodeView.localPasscode = passCode
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

    companion object {
        const val APP_PASS_CODE = "APP_PASS_CODE"
        const val TYPE_OPEN = "TYPE_OPEN"
        const val TITLE_ACTIVITY = "TITLE_ACTIVITY"
        const val MESSAGE_SUCCESS = "MESSAGE_SUCCESS"
    }
}