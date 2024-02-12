package com.murray.repositories

import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.data.items.Item
import com.murray.data.items.ItemId
import com.murray.data.items.ItemType
import com.murray.entities.invoices.Invoice
import com.murray.entities.invoices.InvoiceLine
import com.murray.networkstate.Resource
import com.murray.networkstate.ResourceList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
                Customer("Alberto Sabarit", Email("alberto@gmail.es"), 620400868, "Rincón de la Victoria", "Calle José María Doblas 4"),
                InvoiceLine(
                    Item("Maleta de Cuero",
                    ItemType.Producto,
                    60.00,
                    true,
                    "Cuero de cabra de grano superior, cosido magistralmente con hilo encerado duradero para garantizar una bolsa de lona duradera. El forro interior de la bolsa es de lona resistente.  Tiene hebillas de latón de alta calidad. Hace que tu bolso se vea muy caro. Sólidos herrajes y construcción.",
                ), 2, 21, 60.00), "10/11/2023","25/12/2023")
            )
            return dataSet
        }

        suspend fun createInvoice(invoice: Invoice) : Resource {
            return withContext(Dispatchers.IO) {
                delay(1000)
                dataSet.add(invoice)
                Resource.Success(dataSet)

            }
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