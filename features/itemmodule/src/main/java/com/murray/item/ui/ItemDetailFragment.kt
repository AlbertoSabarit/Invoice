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
                /*
                item.id == 1 -> ivItemDetail.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                item.id == 2 -> ivItemDetail.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                item.id == 3 -> ivItemDetail.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                item.id == 4 -> ivItemDetail.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                item.id == 5 -> ivItemDetail.setImageResource(ImagesItem.OLEO.imagenDrawable)
                item.id == 6 -> ivItemDetail.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)*/
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