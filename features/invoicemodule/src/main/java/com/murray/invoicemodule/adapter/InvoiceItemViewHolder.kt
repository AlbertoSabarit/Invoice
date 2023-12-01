package com.murray.invoicemodule.adapter


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.entities.items.Item
import com.murray.invoicemodule.databinding.LayoutInvoiceItemBinding

class InvoiceItemViewHolder (private val binding: LayoutInvoiceItemBinding): RecyclerView.ViewHolder(binding.root){

    private var deleteClickListener: InvoiceItemViewHolder.OnDeleteClickListener? = null

    fun setOnDeleteClickListener(listener: InvoiceItemViewHolder.OnDeleteClickListener) {
        this.deleteClickListener = listener
    }
    fun bind(item: Item, context: Context) {
        with(binding) {
            txtnArticulo.text = item.name
            txtparticulo.text = item.rate.toString()
            txtptotal.text = item.rate.toString()
        }
        binding.imgQuitarArticulo.setOnClickListener {
            deleteClickListener?.onDeleteClick(item)
        }
    }
    interface OnDeleteClickListener {
        fun onDeleteClick(item: Item)
    }
}