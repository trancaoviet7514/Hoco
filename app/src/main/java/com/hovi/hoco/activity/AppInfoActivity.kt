package com.hovi.hoco.activity

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hovi.hoco.BuildConfig
import com.hovi.hoco.R
import com.hovi.hoco.databinding.ActivityAppInfoBinding


class AppInfoActivity : AppCompatActivity() {

    lateinit var vb: ActivityAppInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAppInfoBinding.inflate(layoutInflater)
        setContentView(vb.root)

        setSupportActionBar(vb.toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vb.txtVersionValue.text = getAppVersionName()
        if (BuildConfig.BUILD_TYPE == "release") {
            vb.layoutBuildTime.visibility = View.GONE
            vb.txtBuildName.text = getString(R.string.app_name)
        } else {
            vb.txtBuildTimeValue.text = BuildConfig.BUILD_TIME
            vb.txtBuildName.text = getString(R.string.app_name) + "_"+ BuildConfig.GIT_BRANCH
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

    private fun getAppVersionName(): String {
        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName, 0)
             return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }
}