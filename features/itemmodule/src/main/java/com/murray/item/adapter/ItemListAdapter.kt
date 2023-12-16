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

        holder.itemView.setOnLongClickListener{
            deleteClickListener(item)
            true
        }
    }

    fun update(newDataSet: ArrayList<Item>) {
        dataset = newDataSet
        notifyDataSetChanged()
    }

    fun sortPersonalizado() {
        dataset.sortBy { it.rate }
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
                    //Elegir imágenes predeterminadas
                    item.name == "Maleta de Cuero" -> imgItem.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                    item.name == "Lápices Acuarela" -> imgItem.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                    item.name == "Cuaderno" -> imgItem.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                    item.name == "Portátil" -> imgItem.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                    item.name == "Pinturas al óleo" -> imgItem.setImageResource(ImagesItem.OLEO.imagenDrawable)
                    item.name == "Botas de nieve" -> imgItem.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)
                    //Imagen galería
                    item.imageUri == null -> imgItem.setImageResource(R.drawable.item_default_image)
                    else -> imgItem.setImageURI(item.imageUri)
                }
            }
        }
    }

}