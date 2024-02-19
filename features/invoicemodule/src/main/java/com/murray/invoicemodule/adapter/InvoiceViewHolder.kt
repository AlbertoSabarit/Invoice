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
            //val cantidadArticulos = item.lineItems.sumBy { it.cantidad }
            //contArticulos.text = cantidadArticulos.toString()

            if (item.lineItems.isNotEmpty()) {
                val nombreArticulo = item.lineItems[0].item.name
                txtnarticulo.text = nombreArticulo
            } else {
                txtnarticulo.text = "No hay artículo"
            }

            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
    }
}