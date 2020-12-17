package com.hovi.hoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.databinding.ActivityConfigServerBinding
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.utils.FireBaseDataBaseUtils
import com.hovi.hoco.utils.hideKeyboard

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

        binding.btnSave.setOnClickListener {
            hideKeyboard()
            val newConnectionString = binding.txtConnectionString.text.toString()

            if (!TextUtils.isEmpty(newConnectionString)) {
                FireBaseDataBaseUtils.updateConnectionString(
                    GlobalData.currentUser!!.userName,
                    newConnectionString,
                    object : FireBaseDataBaseUtils.CallBack {
                        override fun onSuccess(any: Any?) {
                            SharePreferenceUtils.setString(this@ConfigServerActivity, CONNECTION_STRING, newConnectionString)
                            Snackbar.make(binding.root, "Cập nhật thành công", Snackbar.LENGTH_LONG).show()
                        }

                        override fun onFail() {
                            Snackbar.make(binding.root, "Thao tác thất bại", Snackbar.LENGTH_LONG).show()
                        }
                })
            }

        }
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