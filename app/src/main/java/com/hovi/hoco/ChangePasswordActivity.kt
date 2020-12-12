package com.hovi.hoco

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.hovi.hoco.databinding.ActivityChangePasswordBinding
import com.hovi.hoco.databinding.ActivitySignUpBinding

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var vb: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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