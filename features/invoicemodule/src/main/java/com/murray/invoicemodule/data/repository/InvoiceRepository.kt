package com.murray.invoicemodule.data.repository

class InvoiceRepository private constructor() {

    companion object {
        val dataSet: MutableList<com.murray.invoicemodule.data.model.Invoice> = initDataSetUser()

        private fun initDataSetUser(): MutableList<com.murray.invoicemodule.data.model.Invoice> {
            var dataset: MutableList<com.murray.invoicemodule.data.model.Invoice> = ArrayList()
            dataset.add(
                com.murray.invoicemodule.data.model.Invoice(
                    "Katya Nikitenko", "Ferrari SD95", "23/07/2023", "12/05/2024"
                )
            )
            dataset.add(
                com.murray.invoicemodule.data.model.Invoice(
                    "Eliseo", "Audi Q7", "09/03/2024", "12/05/2024"
                )
            )
            dataset.add(
                com.murray.invoicemodule.data.model.Invoice(
                    "Automoviles Nieto", "Ferrari SF90", "21/10/2024", "31/10/2024"
                )
            )
            dataset.add(
                com.murray.invoicemodule.data.model.Invoice(
                    "Alberto", "BMW ", "12/05/2023", "12/04/2026"
                )
            )
            dataset.add(
                com.murray.invoicemodule.data.model.Invoice(
                    "Carlos", "Mercedes", "12/12/2023", "01/05/2024"
                )
            )

            return dataset
        }
    }
}