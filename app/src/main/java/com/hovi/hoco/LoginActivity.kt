package com.hovi.hoco

import android.R
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hovi.hoco.databinding.ActivityLoginBinding
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.User
import com.hovi.hoco.utils.FireBaseDataBaseUtils
import com.hovi.hoco.utils.ViewUtils
import com.hovi.hoco.utils.hideKeyboard


class LoginActivity : AppCompatActivity() {
    lateinit var vb : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.txtVersion.text = "Home Controller Version " + BuildConfig.VERSION_NAME;
        vb.btnLogin.setOnClickListener { view -> run {
            hideKeyboard()
            if (inputValid()) {
                ViewUtils.showLoadingView(vb.root, layoutInflater)
                val userName = vb.txtUserName.text.toString()
                val password = vb.txtPassword.text.toString()
                FireBaseDataBaseUtils.login(userName, password, object : FireBaseDataBaseUtils.SignInCallBack {
                    override fun onSuccess(user: User) {
                        SharePreferenceUtils.setString(this@LoginActivity, CURRENT_USERNAME, user.userName)
                        SharePreferenceUtils.setString(this@LoginActivity, CURRENT_PASSWORD, user.password)
                        SharePreferenceUtils.setString(this@LoginActivity, CURRENT_CONNECTION_STRING, user.connectionString)

                        GlobalData.currentUser = user
                        ViewUtils.removeLoadingView(vb.root)
                        startActivity(Intent(this@LoginActivity, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                    }

                    override fun onFail() {
                        ViewUtils.removeLoadingView(vb.root)
                        Snackbar.make(view, "Tài khoản hoặc mật khẩu không đúng.", Snackbar.LENGTH_LONG).show()
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

        if (TextUtils.isEmpty(userName)) {
            Snackbar.make(vb.root, "Tên đăng nhập phải khác rỗng", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(vb.root, "Mật khẩu phải khác rỗng", Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }

    companion object {
        const val CURRENT_USERNAME = "CURRENT_USERNAME"
        const val CURRENT_PASSWORD = "CURRENT_PASSWORD"
        const val CURRENT_CONNECTION_STRING = "CURRENT_CONNECTION_STRING"
    }
}