package com.murray.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.murray.entities.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.FragmentItemDetailBinding
import com.murray.repositories.ImagesItem

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //TODO cambiar a tipo de datos actual
        val itemName = arguments?.getString("itemName") ?: ""
        val itemType = arguments?.getString("itemType") ?: ""
        val itemRate = arguments?.getDouble("itemRate") ?: ""
        val itemTaxable = arguments?.getBoolean("itemTaxable") ?: false
        val itemDescr = arguments?.getString("itemDescr") ?: ""
        val itemImage = arguments?.getString("itemImage") ?: ""

        with(binding){
            tvItemDetailName.text = itemName
            when(itemType){
                ItemType.PRODUCT.name -> tvItemDetailType.text = context?.getString(R.string.product_string)
                ItemType.SERVICE.name -> tvItemDetailType.text = context?.getString(R.string.service_string)
            }
            tvItemDetailRate.text = "${String.format("%.2f", itemRate)}€"

            if (itemTaxable){
                tvItemDetailTaxable.text = resources.getString(R.string.true_string) //'resources' es lo mismo que usar 'getResources()'
            } else{
                tvItemDetailTaxable.text = resources.getString(R.string.false_string)
            }
            tvItemDetailDescr.text = itemDescr
            ivItemDetail.setImageResource(ImagesItem.getImageDrawable(itemImage));
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}