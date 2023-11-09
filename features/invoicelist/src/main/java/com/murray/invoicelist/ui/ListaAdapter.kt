package com.murray.invoicelist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.murray.invoicelist.R
import com.murray.invoicelist.data.model.Invoice

class ListaAdapter (private val dataset: MutableList<Invoice>, private val context: Context) :
    RecyclerView.Adapter<ListaAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layourInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layourInflater.inflate(com.murray.invoicelist.R.layout.layout_invoice_list, parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvCliente= view.findViewById(com.murray.invoicelist.R.id.txtncliente) as TextView
        val tvArticulos= view.findViewById(com.murray.invoicelist.R.id.txtnarticulo) as TextView
        val tvFCrear = view.findViewById(com.murray.invoicelist.R.id.txtfcreacion) as TextView
        val tvFVenc = view.findViewById(com.murray.invoicelist.R.id.txtfvencimiento) as TextView
        fun bind(item: Invoice, context: Context){
            tvCliente.text = item.cliente
            tvArticulos.text = item.articulo
            tvFCrear.text = item.fcreacion
            tvFVenc.text = item.fvencimiento

        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardview: CardView = itemView.findViewById(R.id.cvcardviewInvoiceList)
    }
}