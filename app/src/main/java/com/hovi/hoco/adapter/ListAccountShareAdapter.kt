package com.hovi.hoco.adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hovi.hoco.R
import com.hovi.hoco.activity.LoginActivity
import com.hovi.hoco.model.GlobalData
import com.hovi.hoco.utils.FireBaseDataBaseUtils

/**
 * Created by VietTC on 20/12/2020.
 */
class ListAccountShareAdapter(val context: Context) : RecyclerView.Adapter<ListAccountShareAdapter.MyViewHolder>() {

    var gmailList : MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_share_permission_access_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gmailList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtGmail.text = gmailList[position]
        holder.btnRemove.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage(HtmlCompat.fromHtml(String.format(context.getString(R.string.str_confirm_remove_account_shared), gmailList[position]), HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(context.getString(R.string.str_yes)) { _, _ ->
                    FireBaseDataBaseUtils.removeAccount2Share(GlobalData.currentUser!!.userName, gmailList[position], null)
                    notifyDataSetChanged()
                }
                .setNegativeButton(context.getString(R.string.str_no), null)
                .show()
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtGmail: TextView = itemView.findViewById(R.id.txt_gmail)
        val btnRemove: ImageView = itemView.findViewById(R.id.btn_remove)
    }
}