package com.murray.customer.ui

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class LayoutTextWatcher(private val til: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        til.error = null
    }
}