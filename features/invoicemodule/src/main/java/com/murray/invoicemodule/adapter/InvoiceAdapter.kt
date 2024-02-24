package com.murray.invoicemodule.adapter

import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.murray.data.invoices.Invoice
import com.murray.data.invoices.LineItems

class InvoiceAdapter(private val listener: onInvoiceClick) : ListAdapter<Invoice,InvoiceViewHolder>(INVOICE_COMPARATOR) {
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
