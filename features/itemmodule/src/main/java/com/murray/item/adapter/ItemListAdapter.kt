package com.murray.item.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.LayoutItemListBinding
import com.murray.repositories.ImagesItem

class ItemListAdapter(
    //private val dataSet: MutableList<Item>,
    private val context: Context,
    private val clickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    private var dataset:ArrayList<Item> = arrayListOf()

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemListViewHolder(LayoutItemListBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = dataset[position]
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
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }

    fun update(newDataSet: ArrayList<Item>){
        //Actualizar mi dataset y notificar a la vista el cambio
        dataset = newDataSet
        notifyDataSetChanged()
    }

    class ItemListViewHolder(val binding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            with(binding) {
                tvNombreText.text = item.name
                when(item.type){
                    ItemType.PRODUCT -> tvTipoText.text = context.getString(R.string.product_string)
                    ItemType.SERVICE -> tvTipoText.text = context.getString(R.string.service_string)
                }

                if (item.isTaxable){
                    tvImpuestoText.text = context.getString(R.string.true_string) //no me deja poner getResources()
                } else{
                    tvImpuestoText.text = context.getString(R.string.false_string)
                }
                tvPrecioText.text = "${String.format("%.2f", item.rate)}€"
                imgItem.setImageResource(ImagesItem.getImageDrawable(item.image.name));
                }
        }
    }

}