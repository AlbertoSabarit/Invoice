package com.murray.invoicemodule.adapter

import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding
import com.murray.invoicemodule.ui.usecase.InvoiceListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InvoiceViewHolder(val binding: LayoutInvoiceListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Invoice, viewModel: InvoiceListViewModel) {
        with(binding) {
            //txtnfacturas.text = "Factura "+ contador
            txtncliente.text = item.cliente.name

            viewModel.getLineItemsForInvoice(item.id).onEach { lineItems ->
                if (lineItems.isNotEmpty()) {
                    val primerItem = lineItems[0]
                    txtnarticulo.text = primerItem.item.name
                    contArticulos.text = primerItem.cantidad.toString() + " x"
                    txtnfacturas.text = "Factura "+  primerItem.invoiceId
                } else {
                    txtnarticulo.text = "No hay artículos"
                }
            }.launchIn(viewModel.viewModelScope)

            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
    }
}