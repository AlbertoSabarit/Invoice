package com.murray.item.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.entities.tasks.Task
import com.murray.item.R
import com.murray.item.databinding.LayoutItemListBinding
import com.murray.repositories.ImagesItem

class ItemListAdapter(
    //private val dataSet: MutableList<Item>,
    private val context: Context,
    private val detailClickListener: (item: Item) -> Unit,
    private val deleteClickListener: (item: Item) -> Unit,
    private val editClickListener: (item: Item) -> Unit
) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    private var dataset: ArrayList<Item> = arrayListOf()

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
            detailClickListener(item)
        }

        holder.binding.imgBtDeleteItem.setOnClickListener {
            deleteClickListener(item)
        }

        holder.binding.imgBtEditItem.setOnClickListener{
            editClickListener(item)
        }
    }

    fun update(newDataSet: ArrayList<Item>) {
        dataset = newDataSet
        notifyDataSetChanged()
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

                if (item.isTaxable) {
                    tvImpuestoText.text =
                        context.getString(R.string.true_string) //no me deja poner getResources()
                } else {
                    tvImpuestoText.text = context.getString(R.string.false_string)
                }
                tvPrecioText.text = "${String.format("%.2f", item.rate)}€"
                when{
                    /*
                    item.id == 1 -> imgItem.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                    item.id == 2 -> imgItem.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                    item.id == 3 -> imgItem.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                    item.id == 4 -> imgItem.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                    item.id == 5 -> imgItem.setImageResource(ImagesItem.OLEO.imagenDrawable)
                    item.id == 6 -> imgItem.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)*/
                    item.imageUri == null -> imgItem.setImageResource(R.drawable.item_default_image)
                    else -> imgItem.setImageURI(item.imageUri)
                }
            }
        }
    }

}