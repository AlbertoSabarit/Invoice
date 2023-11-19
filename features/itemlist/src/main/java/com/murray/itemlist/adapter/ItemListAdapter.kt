package com.murray.itemlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.itemlist.data.model.Item
import com.murray.itemlist.databinding.LayoutItemListBinding

class ItemListAdapter(
    private val dataSet: MutableList<Item>,
    private val context: Context,
    private val clickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)

        //TODO: Que se actualice ItemDetailFragment con los datos de cada Item
        //fun onItemClick(position: Int, item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemListViewHolder(LayoutItemListBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, context)

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
            //TODO: Que se actualice ItemDetailFragment con los datos de cada Item
            //clickListener.onItemClick(position, item)
        }
    }

    class ItemListViewHolder(private val binding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            with(binding) {
                tvNombreText.text = item.nombre
                tvTipoText.text = item.tipo
                tvImpuestoText.text = item.impuesto
                tvPrecioText.text = item.precio
                imgItem.setImageResource(item.imagen)
            }

        }
    }

}