package com.hovi.hoco

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hovi.hoco.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var vb : ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(vb.root)

        mAuth = FirebaseAuth.getInstance();

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("373951975741-o0cgnsf2lfiaf95s9qgirr8gpj6aao8d.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        vb.signInButton.setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            vb.signInButton.id -> signIn()
        }
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
                            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
                        }
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}