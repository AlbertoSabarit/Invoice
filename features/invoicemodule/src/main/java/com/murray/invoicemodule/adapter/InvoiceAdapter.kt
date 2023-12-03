package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceAdapter(private val context: Context) :
    RecyclerView.Adapter<InvoiceViewHolder>() {
    private var dataset = (arrayListOf<Invoice>())
    private var itemClickListener: OnItemClickListener? = null
    private var editClickListener: OnEditClickListener? = null
    private var deleteClickListener: InvoiceViewHolder.OnDeleteClickListener? = null

    fun setOnEditClickListener(listener: OnEditClickListener) {
        this.editClickListener = listener
    }
    fun setOnDeleteClickListener(listener: InvoiceViewHolder.OnDeleteClickListener) {
        this.deleteClickListener = listener
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
        holder.bind(item,context, position+1)

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
                deleteClickListener?.onDeleteClick(item)
            }
        })

    }
    fun update(newDataSet: ArrayList<Invoice>) {
        dataset = newDataSet
        notifyDataSetChanged()
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
