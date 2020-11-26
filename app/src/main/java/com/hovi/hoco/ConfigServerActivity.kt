package com.hovi.hoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.hovi.hoco.databinding.ActivityConfigServerBinding

class ConfigServerActivity : AppCompatActivity() {

    companion object {
        val SERVER_IP = "SERVER_IP"
        val SERVER_PORT = "SERVER_PORT"
    }

    lateinit var binding : ActivityConfigServerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigServerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val host = SharePreferenceUtils.getString(this, SERVER_IP, "tcp://plcshop.webhop.me")
        val port = SharePreferenceUtils.getString(this, SERVER_PORT, "1883")
        binding.txtIp.setText(host)
        binding.txtPort.setText(port)

        binding.btnSaveServerConfig.setOnClickListener(View.OnClickListener {
            SharePreferenceUtils.setString(this, SERVER_IP, binding.txtIp.text.toString())
            SharePreferenceUtils.setString(this, SERVER_PORT, binding.txtPort.text.toString())
            Toast.makeText(this@ConfigServerActivity, "Cập nhật thành công", Toast.LENGTH_LONG).show()
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