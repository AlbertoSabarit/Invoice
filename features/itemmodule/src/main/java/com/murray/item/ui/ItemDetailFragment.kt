package com.murray.item.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.FragmentItemDetailBinding
import com.murray.repositories.ImagesItem

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

        with(binding){
            tvItemDetailName.text = item.name
            when(item.type){
                ItemType.PRODUCT -> tvItemDetailType.text = context?.getString(R.string.product_string)
                ItemType.SERVICE -> tvItemDetailType.text = context?.getString(R.string.service_string)
            }
            tvItemDetailRate.text = "${String.format("%.2f", item.rate)}€"

            if (item.isTaxable){
                tvItemDetailTaxable.text = resources.getString(R.string.true_string) //'resources' es lo mismo que usar 'getResources()'
            } else{
                tvItemDetailTaxable.text = resources.getString(R.string.false_string)
            }
            tvItemDetailDescr.text = item.description

            when{
                //Elegir imágenes predeterminadas
                item.name == "Maleta de Cuero" -> ivItemDetail.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                item.name == "Lápices Acuarela" -> ivItemDetail.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                item.name == "Cuaderno" -> ivItemDetail.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                item.name == "Portátil" -> ivItemDetail.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                item.name == "Pinturas al óleo" -> ivItemDetail.setImageResource(ImagesItem.OLEO.imagenDrawable)
                item.name == "Botas de nieve" -> ivItemDetail.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)
                //Imagen galería
                item.imageUri == null || item.imageUri.toString().isEmpty() -> ivItemDetail.setImageResource(R.drawable.item_default_image)
                else -> ivItemDetail.setImageURI(item.imageUri)
            }
        }

        binding.bItemDetailEditItem.setOnClickListener {
            val item: Item = args.item
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToItemCreationFragment(item)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}