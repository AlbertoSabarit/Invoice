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
            //contArticulos.text = item.articulo.count.toString()
            //txtnarticulo.text =
            //txtnarticulo.text = item.lineItems.item.name

            if (!item.lineItems.isNullOrEmpty()) {
                // Obtiene el primer LineItems de la lista
                val firstLineItem = item.lineItems[0]
                // Establece el nombre del artículo en el TextView
                txtnarticulo.text = firstLineItem.item.name
            } else {
                // Si la lista de lineItems está vacía, muestra un mensaje
                txtnarticulo.text = "No hay artículos"
            }



            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
    }
}