package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceAdapter (private val dataset: MutableList<Invoice>, private val context: Context) :
    RecyclerView.Adapter<InvoiceViewHolder>() {

    private var contador = 0
    private var itemClickListener: OnItemClickListener? = null
    private var editClickListener: OnEditClickListener? = null

    fun setOnEditClickListener(listener: OnEditClickListener) {
        this.editClickListener = listener
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(LayoutInvoiceListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item = dataset.get(position)
        contador++
        holder.bind(item, context, contador)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(item)
        }

        holder.setOnEditClickListener(object : InvoiceViewHolder.OnEditClickListener {
            override fun onEditClick(item: Invoice) {
                editClickListener?.onEditClick(item)
            }
        })

        holder.setOnDeleteClickListener(object : InvoiceViewHolder.OnDeleteClickListener {
            override fun onDeleteClick(item: Invoice) {
                val position = dataset.indexOf(item)
                if (position != -1) {
                    dataset.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        })

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
    interface OnItemClickListener {
        fun onItemClick(item: Invoice)
    }
    interface OnEditClickListener {
        fun onEditClick(item: Invoice)
    }
}
