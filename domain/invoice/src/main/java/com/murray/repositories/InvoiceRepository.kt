package com.murray.repositories

import com.murray.entities.invoices.Invoice
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
            dataSet.add(Invoice(Invoice.lastId++,
                 "Alberto Sabarit", "2 x Cuaderno", "10/11/2023","25/12/2023")
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
                    dataSet.isEmpty()-> ResourceList.NoData(Exception("No hay datos"))
                    else -> ResourceList.Success(dataSet as ArrayList<Invoice>)
                }
            }
        }
    }
}