package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.murray.invoicemodule.R
import com.murray.invoicemodule.data.model.Invoice

class ListaAdapter (private val dataset: MutableList<Invoice>, private val context: Context) :
    RecyclerView.Adapter<ListaAdapter.UserViewHolder>() {
    private var contador = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layourInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layourInflater.inflate(R.layout.layout_invoice_list, parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        contador++
        holder.bind(item, context, contador)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvCliente= view.findViewById(com.murray.invoicemodule.R.id.txtncliente) as TextView
        val tvArticulos= view.findViewById(com.murray.invoicemodule.R.id.txtnarticulo) as TextView
        val tvFCrear = view.findViewById(com.murray.invoicemodule.R.id.txtfcreacion) as TextView
        val tvFVenc = view.findViewById(com.murray.invoicemodule.R.id.txtfvencimiento) as TextView
        val tvFactura = view.findViewById(com.murray.invoicemodule.R.id.txtnfacturas) as TextView

        fun bind(item: Invoice, context: Context, contador: Int){
            tvFactura.text = "Factura " + contador
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