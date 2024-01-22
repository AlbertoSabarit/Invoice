package com.murray.invoicemodule.adapter

import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceViewHolder(val binding: LayoutInvoiceListBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Invoice, contador: Int) {
        with(binding) {
            txtnfacturas.text = "Factura " + contador
            txtncliente.text = item.cliente.name
            contArticulos.text= item.articulo.count.toString()
            txtnarticulo.text = item.articulo.item.name
            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
    }
}


