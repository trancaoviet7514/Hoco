package com.hovi.hoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.databinding.ActivityConfigServerBinding

class ConfigServerActivity : AppCompatActivity() {

    companion object {
        const val CONNECTION_STRING = "CONNECTION_STRING"
    }

    lateinit var binding : ActivityConfigServerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigServerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val host = SharePreferenceUtils.getString(this, CONNECTION_STRING, "tcp://plcshop.webhop.me:1883")
        binding.txtConnectionString.setText(host)

        binding.btnSave.setOnClickListener(View.OnClickListener {
            SharePreferenceUtils.setString(this, CONNECTION_STRING, binding.txtConnectionString.text.toString())
            Snackbar.make(binding.root, "Cập nhật thành công", Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> run {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}