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
import com.murray.task.ui.usecase.TaskCreateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

const val TAG = "ViewModel"
class InvoiceCreateViewModel:ViewModel() {

    var fini =  MutableLiveData<String>()
    var ffin =  MutableLiveData<String>()

    var invoice : Invoice
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

    fun getLineItemsForInvoice(invoiceId: Int): Flow<List<LineItems>> {
        return invoiceRepository.getLineItemsInvoice(invoiceId)
    }


    fun validateCredentials(invoice: Invoice, lineItems: List<LineItems>) {
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
            val invoiceId = invoiceRepository.insert(invoice)

            lineItems.forEach { lineItem ->
                lineItem.invoiceId = invoiceId.toInt()
                itemLineRepository.insert(lineItem, invoiceId.toInt())
            }

            withContext(Dispatchers.Main) {
                state.value = InvoiceCreateState.Success
            }
        }
    }



    fun editInvoiceWithLineItems(invoice: Invoice, lineItems: List<LineItems>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                invoiceRepository.update(invoice)

                itemLineRepository.deleteLineItemsForInvoice(invoice.id)

                lineItems.forEach { lineItem ->
                    lineItem.invoiceId = invoice.id
                    itemLineRepository.insert2(lineItem)
                }

                state.postValue(InvoiceCreateState.Success)
            } catch (e: Exception) {
                state.postValue(InvoiceCreateState.InvoiceCreateError(e.message ?: "Unknown error"))
            }
        }
    }


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