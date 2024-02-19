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
import com.murray.data.tasks.Task
import com.murray.database.dao.LineItemsDao
import com.murray.database.repository.CustomerRepositoryDB
import com.murray.database.repository.InvoiceRepositoryDB
import com.murray.database.repository.ItemRepositoryDB
import com.murray.database.repository.LineItemsRepositoryDB
import com.murray.database.repository.TaskRepositoryDB
import com.murray.networkstate.Resource
import com.murray.repositories.CustomerRepository
import com.murray.repositories.InvoiceRepository
import com.murray.repositories.ItemRepository
import com.murray.task.ui.usecase.TaskCreateState
import kotlinx.coroutines.Delay
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

    fun getState(): LiveData<InvoiceCreateState> {
        return state
    }

    fun validateCredentials(invoice: Invoice){

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
    }

    private suspend fun insertInvoice(invoice: Invoice) {
        withContext(Dispatchers.Main) {
            val result = invoiceRepository.insert(invoice)
            handleResult(result)

        }
    }
     fun insertLineItem(lineItem: LineItems) {
         itemLineRepository.insert(lineItem)
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

    fun getLineItemList(): LiveData<List<LineItems>> {
        var allItemsLine: LiveData<List<LineItems>> = itemLineRepository.getLineItemsList().asLiveData()
        return  allItemsLine
    }

    fun getInvoiceList(): LiveData<List<Invoice>> {
        var allInvoice: LiveData<List<Invoice>> = invoiceRepository.getInvoiceList().asLiveData()
        return  allInvoice
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