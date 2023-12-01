package com.murray.invoicemodule.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceViewHolder(private val binding: LayoutInvoiceListBinding): RecyclerView.ViewHolder(binding.root){

    private var editClickListener: OnEditClickListener? = null
    private var deleteClickListener: OnDeleteClickListener? = null
    fun setOnEditClickListener(listener: OnEditClickListener) {
        this.editClickListener = listener
    }
    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        this.deleteClickListener = listener
    }
    fun bind(item:Invoice, context: Context,contador: Int) {
        with(binding) {
            txtnfacturas.text = "Factura " + contador
            txtncliente.text = item.cliente
            txtnarticulo.text = item.articulo
            txtfcreacion.text = item.fcreacion
            txtfvencimiento.text = item.fvencimiento
        }
        binding.imgbtnEdit.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                editClickListener?.onEditClick(item)
            }
        }
        binding.imgbtnDelete.setOnClickListener {
            deleteClickListener?.onDeleteClick(item)
        }
    }
    interface OnEditClickListener {
        fun onEditClick(item: Invoice)
    }
    interface OnDeleteClickListener {
        fun onDeleteClick(item: Invoice)
    }

}
