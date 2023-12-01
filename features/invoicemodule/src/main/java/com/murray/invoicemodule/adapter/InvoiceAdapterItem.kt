package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.entities.items.Item
import com.murray.invoicemodule.databinding.LayoutInvoiceItemBinding


class InvoiceAdapterItem(private val dataset: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<InvoiceItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceItemViewHolder(LayoutInvoiceItemBinding.inflate(layoutInflater, parent, false))


    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: InvoiceItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item, context)

        holder.setOnDeleteClickListener(object : InvoiceItemViewHolder.OnDeleteClickListener {
            override fun onDeleteClick(item: Item) {
                val position = dataset.indexOf(item)

                if (position != -1) {
                    dataset.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        })
    }
}