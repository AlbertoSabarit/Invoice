package com.murray.data.invoices

import com.murray.data.items.Item

data class InvoiceLine(
    val item : Item,
    val count : Int,
    val iva : Int,
    val price : Double
    )
