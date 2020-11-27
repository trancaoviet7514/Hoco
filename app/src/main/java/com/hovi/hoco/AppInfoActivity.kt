package com.hovi.hoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.hovi.hoco.databinding.ActivityAppInfoBinding

class AppInfoActivity : AppCompatActivity() {

    lateinit var vb: ActivityAppInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAppInfoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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