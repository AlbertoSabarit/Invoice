package com.murray.invoicemodule.adapter

import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murray.data.invoices.Invoice

class InvoiceAdapter(
    private val listener: onInvoiceClick
) :
    ListAdapter<Invoice,InvoiceAdapter.InvoiceViewHolder>(INVOICE_COMPARATOR) {

    interface onInvoiceClick {
        fun clickListener(invoice: Invoice)
        fun userOnLongClickDelete(invoice: Invoice): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(LayoutInvoiceListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.root.setOnClickListener() { _ ->
            listener.clickListener(item)
        }

        holder.binding.root.setOnLongClickListener { _ ->
            listener.userOnLongClickDelete(item)
            true
        }
    }

    fun sort() {
        val sortedTaskList = currentList.sortedBy { it.fcreacion}
        submitList(sortedTaskList)
    }

    inner class InvoiceViewHolder(val binding: LayoutInvoiceListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Invoice) {
            with(binding) {
                txtnfacturas.text = "Factura "
                txtncliente.text = item.cliente.name
                //contArticulos.text = item.articulo.count.toString()
                //txtnarticulo.text = item.articulo.item.name
                txtfcreacion.text = item.fcreacion
                txtfvencimiento.text = item.fvencimiento
            }
        }
    }
    companion object {
        private val INVOICE_COMPARATOR = object : DiffUtil.ItemCallback<Invoice>() {
            override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem.cliente.name == newItem.cliente.name
            }
        }
    }
}
