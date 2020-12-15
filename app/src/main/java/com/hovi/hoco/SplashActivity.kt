package com.hovi.hoco

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.hanks.passcodeview.PasscodeView
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.User

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val passCode = SharePreferenceUtils.getString(this, PassCodeActivity.APP_PASS_CODE)
        if (!TextUtils.isEmpty(passCode)) {
            val intent = Intent(this, PassCodeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra(PassCodeActivity.TYPE_OPEN, PasscodeView.PasscodeViewType.TYPE_CHECK_PASSCODE)
            intent.putExtra(PassCodeActivity.TITLE_ACTIVITY, "Mở khóa ứng dụng")
            intent.putExtra(PassCodeActivity.MESSAGE_SUCCESS, "Xác thực thành công")

            startActivityForResult(intent, 1)
        } else {
            startFlowCheckLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            startFlowCheckLogin()
        }
    }

    private fun startFlowCheckLogin() {
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