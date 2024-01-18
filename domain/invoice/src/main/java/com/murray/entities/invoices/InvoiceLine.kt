package com.murray.entities.invoices

import com.murray.entities.items.Item

data class InvoiceLine(
    val item : Item,
    val invoice: Invoice,
    val count : Int,
    val iva : Int,
    val price : Double
    )
