package com.hovi.hoco.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hovi.hoco.R

/**
 * Created by VietTC on 12/12/2020.
 */
class ViewUtils {
    companion object {
        fun showLoadingView(parent: ViewGroup, inflater: LayoutInflater) {
            try {
                val view = inflater.inflate(R.layout.loading_layout, null)
                val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                parent.addView(view, layoutParams)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun removeLoadingView(parent: ViewGroup) {
            val loadingView = parent.findViewById<ViewGroup>(R.id.loading_view)
            parent.removeView(loadingView)
        }
    }
}