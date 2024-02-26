package com.murray.invoicemodule.adapter

import androidx.recyclerview.widget.RecyclerView
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceViewHolder(val binding: LayoutInvoiceListBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Invoice) {
        with(binding) {
            txtnfacturas.text = "Factura "
            txtncliente.text = item.cliente.name
            //contArticulos.text =


            if (item.lineItems.isNotEmpty()) {
                val primerItem = item.lineItems[0]
                txtnarticulo.text = primerItem.item.name
            } else {
                txtnarticulo.text = "No hay artículos"
            }

            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
    }
}