package com.murray.item.ui.itemcreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.murray.item.databinding.FragmentItemCreationBinding
import com.murray.item.ui.itemcreation.usecase.ItemCreationState
import com.murray.item.ui.itemcreation.usecase.ItemCreationViewModel

class ItemCreationFragment : Fragment() {

    private var _binding: FragmentItemCreationBinding? = null
    private val binding
        get() = _binding!!

    private val itemcreationviewmodel: ItemCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container,false)
        binding.itemcreationviewmodel = this.itemcreationviewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //itemviewmodel.isTaxable.observe(viewLifecycleOwner, { isTaxable -> })

        itemcreationviewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                ItemCreationState.NameEmptyError -> set

            }
        }



    }
}