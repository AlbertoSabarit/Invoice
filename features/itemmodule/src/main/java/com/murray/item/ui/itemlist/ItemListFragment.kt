package com.murray.item.ui.itemlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.items.Item
import com.murray.invoice.MainActivity
import com.murray.invoice.base.BaseFragmentDialog
import com.murray.item.R
import com.murray.item.adapter.ItemListAdapter
import com.murray.item.databinding.FragmentItemListBinding
import com.murray.item.ui.itemlist.usecase.ItemListState
import com.murray.item.ui.itemlist.usecase.ItemListViewModel
import com.murray.repositories.InvoiceRepository
import com.murray.repositories.ItemRepository

class ItemListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!


    private val viewmodel: ItemListViewModel by viewModels()

    private lateinit var itemListAdapter: ItemListAdapter

    private val TAG = "ItemList"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpItemRecycler()
        binding.btnCrearArticulo.setOnClickListener {
            findNavController().navigate(R.id.action_itemListFragment_to_itemCreationFragment)
        }

        viewmodel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is ItemListState.Loading -> showProgressBar(it.value)
                ItemListState.NoDataError -> showNoDataError()
                is ItemListState.Success -> onSuccess(it.dataset)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewmodel.getItemList()
    }

    private fun onSuccess(dataset: ArrayList<Item>) {
        with(binding) {
            lavNoItems.visibility = View.GONE
            tvItemListEmptyTitle.visibility = View.GONE
            tvItemListEmptyText.visibility = View.GONE
            rvItemList.visibility = View.VISIBLE
        }
        itemListAdapter.update(dataset)
    }

    private fun showNoDataError() {
        with(binding) {
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
        itemListAdapter =
            ItemListAdapter(
                requireContext(),
                { viewItemDetail(it) },
                { validateDeleteItem(it) },
                { editItem(it) })

        with(binding.rvItemList) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = itemListAdapter
        }
    }


    private fun viewItemDetail(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item)
        findNavController().navigate(action)
    }

    private fun editItem(item: Item) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemCreationFragment(item)
        findNavController().navigate(action)
    }

    private fun validateDeleteItem(item: Item): Boolean {
        val dialog = BaseFragmentDialog.newInstance(
            "Atención",
            "¿Deseas borrar el artículo?"
        )
        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                var dataSet = viewmodel.getInvoiceRepository()
                if (dataSet.any { invoice -> viewmodel.getInvoiceItemName(invoice.articulo) == item.name }) {
                    Toast.makeText(
                        requireContext(),
                        "No se puede eliminar un artículo asignado a una factura",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewmodel.deleteItem(item,itemListAdapter)
                }
            }
        }
        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_item_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.action_sortTask ->{
                itemListAdapter.sortPersonalizado()
                return true
            }
            R.id.action_refreshTask ->{
                viewmodel.getItemList()
                return true
            }
            else-> false
        }
    }
}