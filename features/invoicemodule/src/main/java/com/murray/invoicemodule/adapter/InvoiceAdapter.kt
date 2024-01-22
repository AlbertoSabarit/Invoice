package com.murray.invoicemodule.adapter

import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice

class InvoiceAdapter(private val listener: onInvoiceClick) :
    RecyclerView.Adapter<InvoiceViewHolder>() {

    private var dataset = (arrayListOf<Invoice>())

    interface onInvoiceClick {
        fun clickListener(invoice: Invoice)
        fun userOnLongClickDelete(invoice: Invoice) : Boolean
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(LayoutInvoiceListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, position+1)

        holder.binding.root.setOnClickListener() { _ ->
            listener.clickListener(item)
        }

        holder.binding.root.setOnLongClickListener { _ ->
            listener.userOnLongClickDelete(item)
            true
        }
    }
    fun update(newDataSet: ArrayList<Invoice>) {
        dataset = newDataSet
        notifyDataSetChanged()
    }
    fun sort(){
        dataset.sortBy { it.cliente.name }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
