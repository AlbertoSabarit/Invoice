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




/*val tvCliente= view.findViewById(R.id.txtncliente) as TextView
  val tvArticulos= view.findViewById(R.id.txtnarticulo) as TextView
  val tvFCrear = view.findViewById(R.id.txtfcreacion) as TextView
  val tvFVenc = view.findViewById(R.id.txtfvencimiento) as TextView
  val tvFactura = view.findViewById(R.id.txtnfacturas) as TextView
  val imgbtnDelete = view.findViewById(R.id.imgbtnDelete) as ImageButton
  val imgbtnEdit =view.findViewById(R.id.imgbtnEdit) as ImageButton*/

/*fun bind(item: com.murray.entities.invoices.Invoice, context: Context, contador: Int){
    tvFactura.text = "Factura " + contador
    tvCliente.text = item.cliente
    tvArticulos.text = item.articulo
    tvFCrear.text = item.fcreacion
    tvFVenc.text = item.fvencimiento

}*/