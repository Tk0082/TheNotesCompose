package com.betrend.cp.thenotes.utils

import android.content.Context
import android.os.Looper
import android.widget.Toast

fun showMessage(context: Context, msg: String?){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun showToast(context: Context, message: String) {
    if (Looper.myLooper() == null) {
        Looper.prepare()
    }
    run {
        showMessage(context, message)
    }
}