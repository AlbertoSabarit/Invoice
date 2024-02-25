package com.murray.invoicemodule.ui.usecase


import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.murray.data.customers.Customer
import com.murray.data.items.Item
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.database.repository.InvoiceRepositoryDB
import com.murray.database.repository.ItemRepositoryDB
import com.murray.database.repository.LineItemsRepositoryDB
import com.murray.networkstate.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

const val TAG = "ViewModel"
class InvoiceCreateViewModel:ViewModel() {

    var fini =  MutableLiveData<String>()
    var ffin =  MutableLiveData<String>()

    lateinit var invoice : Invoice
    private var state = MutableLiveData<InvoiceCreateState>()

    var invoiceRepository = InvoiceRepositoryDB()
    var customerRepository = CustomerRepositoryDB()
    var itemLineRepository = LineItemsRepositoryDB()
    var itemRepository = ItemRepositoryDB()
    init {
        invoice = Invoice()
    }

    fun getState(): LiveData<InvoiceCreateState> {
        return state
    }


   /* private suspend fun editInvoiceWithLineItems(invoice: Invoice, lineItems: List<LineItems>) {
        withContext(Dispatchers.Main) {
             invoiceRepository.update(invoice)
            withContext(Dispatchers.Main) {
                state.value = InvoiceCreateState.Success
            }
        }
    }*/
    /*fun validateCredentials(invoice: Invoice){

        when{
            TextUtils.isEmpty(fini.value) -> state.value = InvoiceCreateState.DataIniEmptyError
            TextUtils.isEmpty(ffin.value) -> state.value = InvoiceCreateState.DataFinEmptyError
            !isValidDateRange(fini.value!!, ffin.value!!) -> state.value = InvoiceCreateState.IncorrectDateRangeError

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (invoice.id == 0) {
                        insertInvoice(invoice)
                    } else {
                        editInvoice(invoice)
                    }
                }
            }
        }
    }*/
    private suspend fun insertInvoice(invoice: Invoice) {
        withContext(Dispatchers.Main) {
            val result = invoiceRepository.insert(invoice)
            handleResult(result)

        }
    }

    //funciona
   /* fun insertInvoiceWithLineItems(invoice: Invoice, lineItems: List<LineItems>) {
        viewModelScope.launch(Dispatchers.IO) {
            val invoiceId = invoiceRepository.insert2(invoice)
            lineItems.forEach { lineItem ->
                lineItem.invoiceId = invoiceId.toInt() // Asigna el ID de la factura a cada elemento de línea
                itemLineRepository.insert(lineItem) // Inserta cada elemento de línea asociado a la factura
            }
        }
    }*/

    fun insertInvoiceWithLineItems(invoice: Invoice, lineItems: List<LineItems>) {
        if (TextUtils.isEmpty(fini.value)) {
            state.value = InvoiceCreateState.DataIniEmptyError
            return
        }
        if (TextUtils.isEmpty(ffin.value)) {
            state.value = InvoiceCreateState.DataFinEmptyError
            return
        }
        if (!isValidDateRange(fini.value!!, ffin.value!!)) {
            state.value = InvoiceCreateState.IncorrectDateRangeError
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val invoiceId = invoiceRepository.insert2(invoice)

                lineItems.forEach { lineItem ->
                    lineItem.invoiceId = invoiceId.toInt()
                    itemLineRepository.insert(lineItem, invoiceId.toInt())
                }

                state.postValue(InvoiceCreateState.Success)
            } catch (e: Exception) {
                state.postValue(InvoiceCreateState.InvoiceCreateError(e.message ?: "Unknown error"))
            }
        }
    }


    fun editInvoiceWithLineItems(invoice: Invoice, lineItems: List<LineItems>) {
        try {
            invoiceRepository.update(invoice)

            // Borra todos los elementos
            //invoiceRepository.delete(invoice)

            lineItems.forEach { lineItem ->
                lineItem.invoiceId = invoice.id
                itemLineRepository.insert(lineItem, invoice.id)
            }

            state.postValue(InvoiceCreateState.Success)
        } catch (e: Exception) {
            state.postValue(InvoiceCreateState.InvoiceCreateError(e.message ?: "Unknown error"))
        }
    }
    /* fun insertLineItem(lineItem: LineItems) {
         itemLineRepository.insert(lineItem)
    }*/


    private suspend fun editInvoice(invoice: Invoice) {
        invoiceRepository.update(invoice)

        withContext(Dispatchers.Main) {
            state.value = InvoiceCreateState.Success
        }
    }

    private fun handleResult(result: Resource) {
        when (result) {
            is Resource.Error -> {
                state.value = InvoiceCreateState.InvoiceCreateError(result.exception.message!!)
            }
            is Resource.Success<*> -> {
                state.value = InvoiceCreateState.Success
            }
        }
    }


    fun getCustomerList(): LiveData<List<Customer>> {
        var allCustomers: LiveData<List<Customer>> = customerRepository.getCustomerList().asLiveData()
        return  allCustomers
    }

    fun getLineItemList(): LiveData<List<LineItems>> {
        var allItemsLine: LiveData<List<LineItems>> = itemLineRepository.getLineItemsList().asLiveData()
        return  allItemsLine
    }


    fun getItemList(): LiveData<List<Item>> {
        var allItem: LiveData<List<Item>> = itemRepository.getItemList().asLiveData()
        return  allItem
    }

    private fun isValidDateRange(startDate: String, endDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val start = dateFormat.parse(startDate)
            val end = dateFormat.parse(endDate)
            return !end.before(start)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
    }
}