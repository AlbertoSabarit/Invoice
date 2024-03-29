package com.murray.item.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.murray.data.items.Item
import com.murray.data.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.FragmentItemDetailBinding
import com.murray.data.items.ImagesItem
import com.murray.data.tasks.Task

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val item: Item = args.item
        initUI(item)

        binding.bItemDetailEditItem.setOnClickListener {
            val item: Item = args.item
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToItemCreationFragment(item)
            findNavController().navigate(action)
        }

        /*
        parentFragmentManager.setFragmentResultListener(
            "editItemResult",
            viewLifecycleOwner
        ) { _, bundle ->
            val updatedItem = bundle.getParcelable<Item>(Item.TAG)
            initUI(updatedItem!!)
            //if (updatedItem != null) item = updatedItem
            //item = updatedItem!!
        }*/
    }

    private fun initUI(item: Item){
        with(binding){
            tvItemDetailName.text = item.name
            when(item.type){
                ItemType.Producto -> tvItemDetailType.text = context?.getString(R.string.product_string)
                ItemType.Servicio -> tvItemDetailType.text = context?.getString(R.string.service_string)
            }
            tvItemDetailRate.text = "${String.format("%.2f", item.rate)}€"
            tvItemDetailTaxable.text = if (item.isTaxable) "Sí" else "No"
            tvItemDetailDescr.text = item.description
            initImage(item, ivItemDetail)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}