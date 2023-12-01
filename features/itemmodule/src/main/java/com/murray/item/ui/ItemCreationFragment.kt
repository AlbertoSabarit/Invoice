package com.murray.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.murray.item.databinding.FragmentItemCreationBinding

class ItemCreationFragment : Fragment() {

    private var _binding: FragmentItemCreationBinding? = null
    private val binding
        get() = _binding!!

    private val itemviewmodel:ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemCreationBinding.inflate(inflater, container,false)
        binding.itemviewmodel = this.itemviewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //itemviewmodel.isTaxable.observe(viewLifecycleOwner, { isTaxable -> })



    }
}