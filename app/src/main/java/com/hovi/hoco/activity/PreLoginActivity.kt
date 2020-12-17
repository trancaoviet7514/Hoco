package com.hovi.hoco.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.BuildConfig
import com.hovi.hoco.databinding.ActivityPreLoginBinding


class PreLoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var vb : ActivityPreLoginBinding
    private var backPressCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityPreLoginBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.txtVersion.text = "Home Controller Version " + BuildConfig.VERSION_NAME;
        vb.btnLogin.setOnClickListener { view -> run {
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        } }

        vb.btnSignUp.setOnClickListener { view -> run {
            startActivity(Intent(this, SignUpActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        } }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            vb.signInButton.id -> signIn()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> run {
                backPressCount++
                if (backPressCount == 2) {
                    finishAffinity()
                } else {
                    Snackbar.make(vb.root, "Nhấn lần nữa để đóng ứng dụng", Snackbar.LENGTH_LONG).show()
                    return true
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }
}