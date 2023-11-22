package com.murray.invoicemodule.adapter

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.invoicemodule.R
import com.murray.entities.invoices.Invoice

class InvoiceViewHolder(view: View): RecyclerView.ViewHolder(view){
    val tvCliente= view.findViewById(R.id.txtncliente) as TextView
    val tvArticulos= view.findViewById(R.id.txtnarticulo) as TextView
    val tvFCrear = view.findViewById(R.id.txtfcreacion) as TextView
    val tvFVenc = view.findViewById(R.id.txtfvencimiento) as TextView
    val tvFactura = view.findViewById(R.id.txtnfacturas) as TextView
    val imgbtnDelete = view.findViewById(R.id.imgbtnDelete) as ImageButton

    fun bind(item: com.murray.entities.invoices.Invoice, context: Context, contador: Int){
        tvFactura.text = "Factura " + contador
        tvCliente.text = item.cliente
        tvArticulos.text = item.articulo
        tvFCrear.text = item.fcreacion
        tvFVenc.text = item.fvencimiento

    }
}
