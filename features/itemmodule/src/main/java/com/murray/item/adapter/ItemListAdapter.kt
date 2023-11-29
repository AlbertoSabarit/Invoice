package com.murray.item.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.items.Item
import com.murray.item.R
import com.murray.item.databinding.LayoutItemListBinding
import com.murray.repositories.ImagesItem

class ItemListAdapter(
    private val dataSet: MutableList<Item>,
    private val context: Context,
    private val clickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Item)
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
            clickListener.onItemClick(item)
        }

        holder.binding.imgBtDeleteItem.setOnClickListener{
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                removeItem(adapterPosition)
            }
        }
    }

    fun removeItem(position: Int){
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }

    class ItemListViewHolder(val binding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            with(binding) {
                tvNombreText.text = item.name
                tvTipoText.text = item.type.name
                if (item.isTaxable){
                    tvImpuestoText.text = context.getString(R.string.true_string) //no me deja poner getResources()
                } else{
                    tvImpuestoText.text = context.getString(R.string.false_string)
                }
                tvPrecioText.text = item.rate.toString()
                imgItem.setImageResource(ImagesItem.getImageDrawable(item.image.name));
                }
        }
    }

}