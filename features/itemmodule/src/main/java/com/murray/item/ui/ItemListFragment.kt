package com.murray.item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.items.Item
import com.murray.item.R
import com.murray.item.adapter.ItemListAdapter
import com.murray.item.databinding.FragmentItemListBinding
import com.murray.repositories.ItemRepository

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

        setUpItemRecycler()
        binding.btnCrearArticulo.setOnClickListener {
            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }
    }

    private fun setUpItemRecycler() {
        var adapter = ItemListAdapter(ItemRepository.dataSet, requireContext(), this)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onItemClick(item: Item) {
        val bundle = bundleOf(
            "itemName" to item.name,
            "itemType" to item.type,
            "itemRate" to item.rate,
            "itemTaxable" to item.isTaxable,
            "itemDescr" to item.description,
            "itemImage" to item.image.name
        )
        findNavController().navigate(R.id.action_itemListFragment_to_itemDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}