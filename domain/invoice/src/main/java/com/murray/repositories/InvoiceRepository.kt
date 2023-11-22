package com.murray.repositories

class InvoiceRepository private constructor() {
    companion object{
        var dataSet:MutableList<com.murray.entities.invoices.Invoice> = initDataSetInvoice()
        private fun initDataSetInvoice():MutableList<com.murray.entities.invoices.Invoice>{
            var dataSet: MutableList<com.murray.entities.invoices.Invoice> = ArrayList()
            dataSet.add(
                com.murray.entities.invoices.Invoice(
                    "Katya Nikitenko",
                    "Ferrari SD95",
                    "23/07/2023",
                    "12/05/2024"
                )
            )
            dataSet.add(
                com.murray.entities.invoices.Invoice(
                    "Eliseo",
                    "Audi Q7",
                    "09/03/2024",
                    "12/05/2024"
                )
            )
            dataSet.add(
                com.murray.entities.invoices.Invoice(
                    "Automoviles Nieto",
                    "Ferrari SF90",
                    "21/10/2024",
                    "31/10/2024"
                )
            )
            dataSet.add(
                com.murray.entities.invoices.Invoice(
                    "Alberto",
                    "BMW ",
                    "12/05/2023",
                    "12/04/2026"
                )
            )
            dataSet.add(
                com.murray.entities.invoices.Invoice(
                    "Carlos",
                    "Mercedes",
                    "12/12/2023",
                    "01/05/2024"
                )
            )
            return dataSet
        }
    }
}