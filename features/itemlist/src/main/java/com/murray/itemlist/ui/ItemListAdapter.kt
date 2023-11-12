package com.murray.itemlist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.itemlist.R
import com.murray.itemlist.databinding.LayoutItemListBinding

class ItemListAdapter(private val dataset: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemListViewHolder(LayoutItemListBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    class ItemListViewHolder(private val binding: LayoutItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            with(binding){
                tvNombreText.text = item.nombre
                tvTipoText.text = item.tipo
                tvImpuestoText.text = item.impuesto
                tvPrecioText.text = item.precio
                imgItem.setImageResource(item.imagen)
            }

        }
    }

}