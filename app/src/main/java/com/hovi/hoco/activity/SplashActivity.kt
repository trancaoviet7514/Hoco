package com.hovi.hoco.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hanks.passcodeview.PasscodeView
import com.hovi.hoco.R
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.User
import com.hovi.hoco.utils.FireBaseDataBaseUtils
import com.hovi.hoco.utils.SharePreferenceUtils

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
        if (FirebaseAuth.getInstance().currentUser != null) {
            GlobalData.currentUser = FirebaseAuth.getInstance().currentUser?.let { User(it) }
            startActivity(Intent(this, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        } else {
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }
}