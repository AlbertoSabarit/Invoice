package com.murray.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.murray.item.R
import com.murray.item.databinding.FragmentItemDetailBinding

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

        val itemName = arguments?.getString("itemName") ?: ""
        val itemType = arguments?.getString("itemType") ?: ""
        val itemRate = arguments?.getString("itemRate") ?: ""
        val itemTaxable = arguments?.getBoolean("itemTaxable") ?: false
        val itemDescr = arguments?.getString("itemDescr") ?: ""
        val itemImage = arguments?.getInt("itemImage") ?: 0

        with(binding){
            tvItemDetailName.text = itemName
            tvItemDetailType.text = itemType
            tvItemDetailRate.text = itemRate

            if (itemTaxable){
                tvItemDetailTaxable.text = resources.getString(R.string.true_string) //'resources' es lo mismo que usar 'getResources()'
            } else{
                tvItemDetailTaxable.text = resources.getString(R.string.false_string)
            }

            tvItemDetailDescr.text = itemDescr
            ivItemDetail.setImageResource(itemImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}