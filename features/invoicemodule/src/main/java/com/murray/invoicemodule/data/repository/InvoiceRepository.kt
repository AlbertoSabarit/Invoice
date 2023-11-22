package com.murray.invoicemodule.data.repository

import com.murray.invoicemodule.data.model.Invoice

class InvoiceRepository private constructor() {
    companion object{
        var dataSet:MutableList<Invoice> = initDataSetInvoice()
        private fun initDataSetInvoice():MutableList<Invoice>{
            var dataSet: MutableList<Invoice> = ArrayList()
            dataSet.add(Invoice("Katya Nikitenko", "Ferrari SD95", "23/07/2023", "12/05/2024"))
            dataSet.add(Invoice("Eliseo", "Audi Q7", "09/03/2024", "12/05/2024"))
            dataSet.add(Invoice("Automoviles Nieto", "Ferrari SF90", "21/10/2024", "31/10/2024"))
            dataSet.add(Invoice("Alberto", "BMW ", "12/05/2023", "12/04/2026"))
            dataSet.add(Invoice("Carlos", "Mercedes", "12/12/2023", "01/05/2024"))
            return dataSet
        }
    }
}