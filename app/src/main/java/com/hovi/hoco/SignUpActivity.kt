package com.hovi.hoco

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.databinding.ActivitySignUpBinding
import com.hovi.hoco.utils.FireBaseDataBaseUtils
import com.hovi.hoco.utils.ViewUtils

class SignUpActivity : AppCompatActivity() {
    lateinit var vb : ActivitySignUpBinding
    val UIHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.txtVersion.text = "Home Controller Version " + BuildConfig.VERSION_NAME;
        vb.btnSignUp.setOnClickListener { view -> run {
            if (inputValid()) {
                ViewUtils.showLoadingView(vb.root, layoutInflater)
                val userName = vb.txtUserName.text.toString()
                val password = vb.txtPassword.text.toString()
                val connectString = vb.txtConnectString.text.toString()

                FireBaseDataBaseUtils.signUp(userName, password, connectString, object : FireBaseDataBaseUtils.SignUpCallBack {
                    override fun onSuccess() {
                        ViewUtils.removeLoadingView(vb.root)
                        Snackbar.make(view, "Đăng ký thành công", Snackbar.LENGTH_LONG).show()
                        UIHandler.postDelayed(Runnable { startActivity(Intent(this@SignUpActivity, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                        }, 1000)
                    }

                    override fun onFail() {
                        ViewUtils.removeLoadingView(vb.root)
                        Snackbar.make(view, "Tài khoản đã tồn tại", Snackbar.LENGTH_LONG).show()
                    }

                })
            }
        } }
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

    private fun inputValid(): Boolean{
        val userName = vb.txtUserName.text.toString()
        val password = vb.txtPassword.text.toString()
        val reenterPassword = vb.txtReenterPassword.text.toString()
        val connectionString = vb.txtConnectString.text.toString()

        if (TextUtils.isEmpty(userName)) {
            Snackbar.make(vb.root, "Chưa có tên đăng nhập", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(vb.root, "Chưa có mật khẩu", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (reenterPassword != password) {
            Snackbar.make(vb.root, "Mật khẩu nhập lại chưa khớp", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(connectionString)) {
            Snackbar.make(vb.root, "Chưa có chuỗi kết nối", Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }
}