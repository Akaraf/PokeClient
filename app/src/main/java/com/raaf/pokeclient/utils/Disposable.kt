package com.raaf.pokeclient.utils

import android.util.Log

fun onFailure(t: Throwable) {
    Log.e("Disposable exception", t.message.toString())
}