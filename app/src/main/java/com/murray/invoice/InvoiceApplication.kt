package com.murray.invoice

import android.app.Application

class InvoiceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)
    }
}