package com.murray.item.ui

import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val itemName = arguments?.getString("itemName") ?: ""
        val itemType = arguments?.getString("itemType") ?: ""
        val itemRate = arguments?.getString("itemRate") ?: ""
        val itemTaxable = arguments?.getBoolean("itemTaxable") ?: false
        val itemDescr = arguments?.getString("itemDescr") ?: ""
        val itemImage = arguments?.getString("itemImage") ?: ""

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
            ivItemDetail.setImageResource(ImagesItem.getImageDrawable(itemImage));
            /* (sin meter en funcion)
            var imageDrawable:Int = R.drawable.img_maleta_cuero; //placeholder

            when (itemImage){
                ImagesItem.MALETA_CUERO.name -> imageDrawable = com.murray.invoice.R.drawable.img_maleta_cuero
                ImagesItem.LAPICES_ACUARELA.name -> imageDrawable = com.murray.invoice.R.drawable.img_lapices_acuarela
                ImagesItem.CUADERNO.name -> imageDrawable = com.murray.invoice.R.drawable.img_cuaderno
                ImagesItem.PORTATIL.name -> imageDrawable = com.murray.invoice.R.drawable.img_portatil
                ImagesItem.OLEO.name -> imageDrawable = com.murray.invoice.R.drawable.img_oleo
                ImagesItem.BOTAS_NIEVE.name -> imageDrawable = com.murray.invoice.R.drawable.img_botas_nieve
            }*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}