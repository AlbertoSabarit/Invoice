package com.murray.item.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.murray.entities.items.Item
import com.murray.entities.items.ItemType
import com.murray.item.R
import com.murray.item.databinding.FragmentItemDetailBinding
import com.murray.repositories.ImagesItem

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!
    //private val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //val item: Item = args.item
        val itemId = arguments?.getInt("itemId") ?: 1
        val itemName = arguments?.getString("itemName") ?: ""
        val itemType = arguments?.getString("itemType") ?: ""
        val itemRate = arguments?.getDouble("itemRate") ?: ""
        val itemTaxable = arguments?.getBoolean("itemTaxable") ?: false
        val itemDescr = arguments?.getString("itemDescr") ?: ""
        val itemImageString = arguments?.getString("itemImageString") ?: ""

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

            when{
                itemId == 1 -> ivItemDetail.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                itemId == 2 -> ivItemDetail.setImageResource(ImagesItem.LAPICES_ACUARELA.imagenDrawable)
                itemId == 3 -> ivItemDetail.setImageResource(ImagesItem.CUADERNO.imagenDrawable)
                itemId == 4 -> ivItemDetail.setImageResource(ImagesItem.PORTATIL.imagenDrawable)
                itemId == 5 -> ivItemDetail.setImageResource(ImagesItem.OLEO.imagenDrawable)
                itemId == 6 -> ivItemDetail.setImageResource(ImagesItem.BOTAS_NIEVE.imagenDrawable)
                itemImageString.isNullOrEmpty() -> ivItemDetail.setImageResource(ImagesItem.MALETA_CUERO.imagenDrawable)
                else -> ivItemDetail.setImageURI(Uri.parse(itemImageString))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}