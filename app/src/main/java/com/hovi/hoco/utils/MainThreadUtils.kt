@file:JvmName("MainThreadUtils")

package com.hovi.hoco.utils

import android.os.Handler
import android.os.Looper

/**
 * Created by VietTC on 12/12/2020.
 */

private val handler = Handler(Looper.getMainLooper())

fun post(r: Runnable) {
    handler.post(r)
}

fun postDelayed(
        r: Runnable,
        delayMillis: Long
) {
    handler.postDelayed(r, delayMillis)
}

fun removeCallbacks(r: Runnable) {
    handler.removeCallbacks(r)
}

fun run(runnable: Runnable) {
    if (isMainThread()) {
        runnable.run()
    } else {
        post(runnable)
    }
}

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}