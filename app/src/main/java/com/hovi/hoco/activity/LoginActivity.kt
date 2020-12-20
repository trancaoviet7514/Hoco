package com.hovi.hoco.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hovi.hoco.BuildConfig
import com.hovi.hoco.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var vb : ActivityLoginBinding
    private var backPressCount = 0;
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.txtVersion.text = "Home Controller Version " + BuildConfig.VERSION_NAME;

        mAuth = FirebaseAuth.getInstance();

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("373951975741-o0cgnsf2lfiaf95s9qgirr8gpj6aao8d.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        vb.btnLogin.setOnClickListener { view -> run {
            signIn()
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

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

                mAuth!!.signInWithCredential(credential)
                        .addOnCompleteListener(this) { task1 ->
                            if (task1.isSuccessful) {
                                startActivity(Intent(this, RemoteActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                            } else {
                                Snackbar.make(vb.root, "Vui lòng kiểm tra kết nối mạng!", Snackbar.LENGTH_LONG).show()
                            }
                        }

            } catch (e: Exception) {
                e.printStackTrace()
                Snackbar.make(vb.root, "Vui lòng kiểm tra kết nối mạng!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val CURRENT_CONNECTION_STRING = "CURRENT_CONNECTION_STRING"
    }
}