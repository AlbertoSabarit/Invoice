package com.murray.invoicemodule.ui.usecase


import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.invoices.Invoice
import com.murray.repositories.InvoiceRepository
import com.murray.repositories.ItemRepository
import kotlinx.coroutines.launch
import java.util.Locale

const val TAG = "ViewModel"
class InvoiceCreateViewModel:ViewModel() {

    var fini =  MutableLiveData<String>()
    var ffin =  MutableLiveData<String>()
    lateinit var invoice : Invoice
    private var state = MutableLiveData<InvoiceCreateState>()

    fun validateCredentials(){

        when{
            TextUtils.isEmpty(fini.value) -> state.value = InvoiceCreateState.DataIniEmptyError
            TextUtils.isEmpty(ffin.value) -> state.value = InvoiceCreateState.DataFinEmptyError
            !isValidDateRange(fini.value!!, ffin.value!!) -> state.value = InvoiceCreateState.IncorrectDateRangeError

            else -> {
                viewModelScope.launch {
                    state.value = InvoiceCreateState.Loading(true)
                    //val result = InvoiceRepository.createInvoice(fini.value!!, ffin.value!!)
                    state.value = InvoiceCreateState.Loading(false)
                    state.value = InvoiceCreateState.Success


                }
            }
        }
    }

    fun editInvoice(invoiceTmp: Invoice){
        for (inv in InvoiceRepository.dataSet) {
            if (inv.id == invoice.id) {
                inv.cliente = invoiceTmp.cliente
                inv.articulo = invoiceTmp.articulo
                inv.fcreacion = invoiceTmp.fcreacion
                inv.fvencimiento = invoiceTmp.fvencimiento

            }
        }
    }
    fun addToList(invoice: Invoice){
        InvoiceRepository.addInvoice(invoice)
    }
    fun getPrecio(nombreArticulo: String?): Double {
        val articuloSeleccionado = ItemRepository.getDataSetItem().find { it.name == nombreArticulo }
        return articuloSeleccionado?.rate ?: 0.0
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
    fun getState(): LiveData<InvoiceCreateState> {
        return state
    }
}