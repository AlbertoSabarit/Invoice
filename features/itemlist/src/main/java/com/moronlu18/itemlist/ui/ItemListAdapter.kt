package com.moronlu18.itemlist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.itemlist.R

class ItemListAdapter(private val dataset: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemListViewHolder(layoutInflater.inflate(R.layout.layout_item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    class ItemListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre = view.findViewById(R.id.tvNombreText) as TextView
        val tvTipo = view.findViewById(R.id.tvTipoText) as TextView
        val tvImpuesto = view.findViewById(R.id.tvImpuestoText) as TextView
        val tvPrecio = view.findViewById(R.id.tvPrecioText) as TextView

        fun bind(item: Item, context: Context) {
            tvNombre.text = item.nombre
            tvTipo.text = item.tipo
            tvImpuesto.text = item.impuesto
            tvPrecio.text = item.precio
        }
    }

}