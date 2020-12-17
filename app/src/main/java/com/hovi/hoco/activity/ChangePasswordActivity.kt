package com.hovi.hoco.activity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.hovi.hoco.databinding.ActivityChangePasswordBinding
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.utils.FireBaseDataBaseUtils
import com.hovi.hoco.utils.hideKeyboard

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var vb: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.btnChangePassword.setOnClickListener(View.OnClickListener {
            hideKeyboard()
            if (inputValid()) {
                val newPassword = vb.txtNewPassword.text.toString()
                FireBaseDataBaseUtils.changePassword(GlobalData.currentUser!!.userName, newPassword, object : FireBaseDataBaseUtils.CallBack {
                    override fun onSuccess(any: Any?) {
                        GlobalData.currentUser!!.password = newPassword
                        Snackbar.make(vb.root, "Đổi mật khẩu thành công", Snackbar.LENGTH_LONG).show()
                    }

                    override fun onFail() {
                    }

                })
            }
        })
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
        val currentPassword = vb.txtCurrentPassword.text.toString()
        val newPassword = vb.txtNewPassword.text.toString()
        val reenterNewPassword = vb.txtReenterPassword.text.toString()

        if (currentPassword != GlobalData.currentUser!!.password) {
            Snackbar.make(vb.root, "Mật khẩu hiện tại chưa chính xác", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(newPassword)) {
            Snackbar.make(vb.root, "Mật khẩu mới phải khác rỗng", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (newPassword != reenterNewPassword) {
            Snackbar.make(vb.root, "Mật khẩu nhập lại chưa khớp", Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }
}