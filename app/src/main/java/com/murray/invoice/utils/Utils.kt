package com.murray.invoice.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Utils {
    //Esta función de extensión de podrá llamar desde cualquier Activity/o fragmento a través de su Activity
    fun Context.showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.showSnackBar(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }
}