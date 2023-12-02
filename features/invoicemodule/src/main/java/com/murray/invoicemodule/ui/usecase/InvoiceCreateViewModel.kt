package com.murray.invoicemodule.ui.usecase

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murray.entities.invoices.Invoice
import com.murray.network.Resource
import com.murray.repositories.InvoiceRepository
import kotlinx.coroutines.launch
import java.util.Locale

class InvoiceCreateViewModel:ViewModel() {

    var fini =  MutableLiveData<String>()
    var ffin =  MutableLiveData<String>()

    private var state = MutableLiveData<InvoiceCreateState>()

    fun validateCredentials(){
        when{
            TextUtils.isEmpty(fini.value) -> state.value = InvoiceCreateState.DataIniEmptyError
            TextUtils.isEmpty(ffin.value) -> state.value = InvoiceCreateState.DataFinEmptyError
            !isValidDateRange(fini.value!!, ffin.value!!) -> state.value = InvoiceCreateState.IncorrectDateRangeError


            else -> {
                viewModelScope.launch {
                    state.value = InvoiceCreateState.Loading(true)
                    val result = InvoiceRepository.createInvoice(fini.value!!, ffin.value!!)
                    state.value = InvoiceCreateState.Loading(false)

                    when(result){
                        is Resource.Success<*>->{
                            Log.i(ContentValues.TAG, "Informacion del dato ${result.data}")
                            state.value = InvoiceCreateState.Success(result.data as Invoice)
                        }
                        is Resource.Error->{
                            Log.i(ContentValues.TAG, "Informacion del dato ${result.exception.message}")
                            state.value = InvoiceCreateState.InvoiceCreateError(result.exception.message!!)
                        }
                    }
                }
            }
        }
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