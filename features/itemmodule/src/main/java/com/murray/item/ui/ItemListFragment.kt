package com.murray.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.item.adapter.ItemListAdapter
import com.murray.item.R
import com.murray.repositories.ItemRepository
import com.murray.item.databinding.FragmentItemListBinding

class ItemListFragment : Fragment(), ItemListAdapter.OnItemClickListener {

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUserRecycler()
        binding.btnCrearArticulo.setOnClickListener{
            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
            //TODO: Que se actualice ItemDetailFragment con los datos de cada Item
            //val action = ItemListFragmentDirections
        }
    }

    private fun setUpUserRecycler(){
        var adapter = ItemListAdapter (ItemRepository.dataSet, requireContext(), this)

        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }

    override fun onItemClick(position: Int) {
       findNavController().navigate(R.id.action_itemListFragment_to_itemDetailFragment)
    }

}