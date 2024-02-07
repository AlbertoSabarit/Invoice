package com.murray.item.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.LayoutItemListBinding
import com.murray.data.items.ImagesItem
import com.murray.data.tasks.Task

class ItemListAdapter(
    private val context: Context,
    private val detailClickListener: (item: Item) -> Unit,
    private val deleteClickListener: (item: Item) -> Unit,
) :
    ListAdapter<Item, ItemListAdapter.ItemListViewHolder>(ITEM_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemListViewHolder(LayoutItemListBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)

        holder.itemView.setOnClickListener {
            detailClickListener(item)
        }

        holder.itemView.setOnLongClickListener{
            deleteClickListener(item)
            true
        }
    }

    fun sortPrecio() {
        val sortedItemList = currentList.sortedBy {it.rate}
        submitList(sortedItemList)
    }

    class ItemListViewHolder(val binding: LayoutItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, context: Context) {
            with(binding) {
                tvNombreText.text = item.name
                when (item.type) {
                    ItemType.PRODUCT -> tvTipoText.text = context.getString(R.string.product_string)
                    ItemType.SERVICE -> tvTipoText.text = context.getString(R.string.service_string)
                }
                tvImpuestoText.text = if (item.isTaxable) context.getString(R.string.true_string) else context.getString(R.string.false_string)
                tvPrecioText.text = "${String.format("%.2f", item.rate)}€"
                initImage(item, imgItem)
            }
        }

        private fun initImage(item: Item, imageView: ImageView) {
            when(item.name){
                "Maleta de Cuero" -> imageView.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                "Lápices Acuarela" -> imageView.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                "Cuaderno" -> imageView.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                "Portátil" -> imageView.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                "Pinturas al óleo" -> imageView.setImageResource(ImagesItem.OLEO.imagenDrawable)
                "Botas de nieve" -> imageView.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)
                else ->  {
                    if (item.imageUri == null || TextUtils.isEmpty(item.imageUri.toString())) {
                        imageView.setImageResource(R.drawable.item_default_image)
                    } else {
                        imageView.setImageURI(item.imageUri)
                    }
                }
            }
        }
    }

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}