package com.murray.item.ui

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
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
            tvItemDetailTaxable.text = if (item.isTaxable) "Sí" else "No"
            tvItemDetailDescr.text = item.description

            when(item.name){
                "Maleta de Cuero" -> ivItemDetail.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                "Lápices Acuarela" -> ivItemDetail.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                "Cuaderno" -> ivItemDetail.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                "Portátil" -> ivItemDetail.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                "Pinturas al óleo" -> ivItemDetail.setImageResource(ImagesItem.OLEO.imagenDrawable)
                "Botas de nieve" -> ivItemDetail.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)
                else ->  {
                    if (item.imageUri == null || TextUtils.isEmpty(item.imageUri.toString())) {
                        ivItemDetail.setImageResource(R.drawable.item_default_image)
                    } else {
                        ivItemDetail.setImageURI(item.imageUri)
                    }
                }
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