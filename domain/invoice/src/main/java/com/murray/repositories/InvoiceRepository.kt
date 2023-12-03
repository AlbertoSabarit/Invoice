package com.murray.repositories

import com.murray.entities.invoices.Invoice
import com.murray.entities.tasks.Task
import com.murray.network.Resource
import com.murray.network.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class InvoiceRepository private constructor() {
    companion object{
        var dataSet:MutableList<Invoice> = mutableListOf()

        init {
            initDataSetInvoice()
        }


        fun addInvoice(i: Invoice){
            dataSet.add(i)
        }
        private fun initDataSetInvoice():MutableList<Invoice>{
            dataSet.add(Invoice(
                    "Katya Nikitenko",
                    "Ferrari SD95",
                    "23/07/2023",
                    "12/05/2024"
                )
            )
            dataSet.add(Invoice(
                    "Eliseo",
                    "Audi Q7",
                    "09/03/2024",
                    "12/05/2024"
                )
            )
            dataSet.add(Invoice(
                    "Automoviles Nieto",
                    "Ferrari SF90",
                    "21/10/2024",
                    "31/10/2024"
                )
            )
            dataSet.add(Invoice(
                    "Alberto",
                    "BMW ",
                    "12/05/2023",
                    "12/04/2026"
                )
            )
            dataSet.add(Invoice(
                    "Carlos",
                    "Mercedes",
                    "12/12/2023",
                    "01/05/2024"
                )
            )
            return dataSet
        }

        suspend fun createInvoice(fini: String, ffin: String) : Resource {
            withContext(Dispatchers.IO){
                delay(2000)
            }
            return Resource.Error(Exception("La fecha incorrecta"))
        }

        suspend fun getInvoiceList(): ResourceList {
            return withContext(Dispatchers.IO) {
                delay(2000)
                when {
                    dataSet.isEmpty()-> ResourceList.Error(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Invoice>)
                }
            }
        }
    }
}