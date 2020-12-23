package com.hovi.hoco.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.hovi.hoco.R
import com.hovi.hoco.adapter.ListAccountShareAdapter
import com.hovi.hoco.databinding.ActivityManagingAccessBinding
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.model.getUserNameFromEmail
import com.hovi.hoco.utils.FireBaseDataBaseUtils


class ManagingAccessActivity : AppCompatActivity() {

    lateinit var vb: ActivityManagingAccessBinding
    lateinit var adapter: ListAccountShareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityManagingAccessBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ListAccountShareAdapter(this)
        vb.rcvListAccountShared.layoutManager = LinearLayoutManager(this)
        vb.rcvListAccountShared.adapter = adapter

        GlobalData.currentUser!!.userName.let { FireBaseDataBaseUtils.getListAccount2Shared(it, object : FireBaseDataBaseUtils.CallBack {
            override fun onSuccess(any: Any?) {
                if (any is MutableSet<*>) {
                    adapter.gmailList.clear()
                    adapter.gmailList.addAll(any as Collection<String>)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFail() {
            }

        }) }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.manaing_access_activity_tool_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_new -> run {
                showDialogAddNewAccount()
                return true
            }

            android.R.id.home -> run {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogAddNewAccount() {
        val contentView = layoutInflater.inflate(R.layout.dialog_add_new_account_to_share, null)

        val dialog = AlertDialog.Builder(this)
            .setView(contentView)
            .show()

        val txtGmail = contentView.findViewById<EditText>(R.id.txt_email)

        val btnOK = contentView.findViewById<TextView>(R.id.btn_ok)
        btnOK.setOnClickListener {
            val shareMail = getUserNameFromEmail(txtGmail.text.toString())
            if (shareMail != GlobalData.currentUser!!.userName) {
                FireBaseDataBaseUtils.addNewAccount2Share(GlobalData.currentUser!!.userName, shareMail, null)
                dialog.dismiss()
            } else {
                val txtErrorMessage = contentView.findViewById<TextView>(R.id.txt_erorr_message)
                txtErrorMessage.text = getString(R.string.str_error_account_share_have_to_difference_current_account)
                txtErrorMessage.visibility = View.VISIBLE
            }
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (TextUtils.isEmpty(s)) {
                    btnOK.visibility = View.GONE
                } else {
                    btnOK.visibility = View.VISIBLE
                }
            }
        }

        txtGmail.addTextChangedListener(textWatcher)

        val btnCancel = contentView.findViewById<TextView>(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}