package com.murray.invoice

import android.app.Application
import com.google.firebase.FirebaseApp

class InvoiceApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)

        //Se inicializa la conecion a Firease (SOLO PUEDE ESTAR AQUI)
        FirebaseApp.initializeApp(this)
    }
}