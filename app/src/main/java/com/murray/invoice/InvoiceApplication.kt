package com.murray.invoice

import android.app.Application
import com.google.firebase.FirebaseApp

class InvoiceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)

    }
}