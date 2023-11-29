package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.R
import com.murray.invoicemodule.databinding.LayoutInvoiceListBinding

class InvoiceAdapter (private val dataset: MutableList<Invoice>, private val context: Context) :
    RecyclerView.Adapter<InvoiceViewHolder>() {
    private var contador = 0
    private var itemClickListener: OnItemClickListener? = null
    private var editClickListener: InvoiceViewHolder.OnEditClickListener? = null
    interface OnItemClickListene {
        fun onEditButtonClick(item: Invoice)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        /*val layourInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(layourInflater.inflate(R.layout.layout_invoice_list, parent,false))*/
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

        /*holder.imgbtnDelete.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                removeItem(adapterPosition)
            }
        }*/

        holder.setOnEditClickListener(object : InvoiceViewHolder.OnEditClickListener {
            override fun onEditClick(item: Invoice) {
                editClickListener?.onEditClick(item)
            }
        })
        holder.setOnDeleteClickListener(object : InvoiceViewHolder.OnDeleteClickListener {
            override fun onDeleteClick(item: Invoice) {
                // Manejar el evento de eliminación aquí, por ejemplo, eliminar el elemento de la lista
                val position = dataset.indexOf(item)
                if (position != -1) {
                    dataset.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        })


    }

    fun removeItem(position: Int) {
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
    interface OnItemClickListener {
        fun onItemClick(item: Invoice)
    }
}