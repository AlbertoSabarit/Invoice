package com.murray.item.ui.itemlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.items.Item
import com.murray.item.R
import com.murray.item.adapter.ItemListAdapter
import com.murray.item.databinding.FragmentItemListBinding
import com.murray.item.ui.itemlist.usecase.ItemListState
import com.murray.item.ui.itemlist.usecase.ItemListViewModel
import com.murray.repositories.ItemRepository

class ItemListFragment : Fragment(){

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!

    private val itemlistviewmodel :ItemListViewModel by viewModels()

    private lateinit var itemListAdapter: ItemListAdapter

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

        itemlistviewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                is ItemListState.Loading -> showProgressBar(it.value)
                ItemListState.NoDataError -> showNoDataError()
                is ItemListState.Success -> onSuccess(it.dataset)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        itemlistviewmodel.getItemList()
    }

    private fun onSuccess(dataset: ArrayList<Item>) {
        with(binding){
            lavNoItems.visibility = View.GONE
            tvItemListEmptyTitle.visibility = View.GONE
            tvItemListEmptyText.visibility = View.GONE
            rvItemList.visibility = View.VISIBLE
        }
        itemListAdapter.update(dataset)
    }

    private fun showNoDataError() {
        with(binding){
            lavNoItems.visibility = View.VISIBLE
            tvItemListEmptyTitle.visibility = View.VISIBLE
            tvItemListEmptyText.visibility = View.VISIBLE
            rvItemList.visibility = View.GONE
        }
    }

    private fun showProgressBar(value: Boolean) {
        if (value)
            findNavController().navigate((R.id.action_itemListFragment_to_fragmentProgressDialog))
        else
            findNavController().popBackStack()
    }

    private fun setUpItemRecycler() {
        itemListAdapter = ItemListAdapter(requireContext(), {viewItemDetail(it)},{deleteItem(it)})

        with(binding.rvItemList) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = itemListAdapter
        }
    }

    fun viewItemDetail(item: Item) {
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

    fun deleteItem(item: Item){
        ItemRepository.getDataSetItem().remove(item)
        itemListAdapter.update(ItemRepository.getDataSetItem() as ArrayList<Item>)
        if (ItemRepository.getDataSetItem().isEmpty()){
            showNoDataError()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}