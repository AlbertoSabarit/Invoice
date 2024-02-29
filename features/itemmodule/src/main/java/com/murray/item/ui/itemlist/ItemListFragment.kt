package com.murray.item.ui.itemlist

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hanmajid.android.tiramisu.notificationruntimepermission.createNotificationChannel
import com.hanmajid.android.tiramisu.notificationruntimepermission.sendNotification
import com.murray.data.items.Item
import com.murray.invoice.ui.MainActivity
import com.murray.invoice.base.BaseFragmentDialog
import com.murray.item.R
import com.murray.item.adapter.ItemListAdapter
import com.murray.item.databinding.FragmentItemListBinding
import com.murray.item.ui.ItemDetailFragmentArgs
import com.murray.item.ui.ItemDetailFragmentDirections
import com.murray.item.ui.itemlist.usecase.ItemListState
import com.murray.item.ui.itemlist.usecase.ItemListViewModel

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

        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.itemListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        binding.btnCrearArticulo.setOnClickListener {
            val emptyItem = Item()
            emptyItem.id = -1
            val action = ItemListFragmentDirections.actionItemListFragmentToItemCreationFragment(emptyItem)
            findNavController().navigate(action)
        }

        setUpToolbar()
        setUpItemRecycler()


        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ItemListState.Loading -> showProgressBar(it.value)
                ItemListState.NoDataError -> showNoDataError()
                ItemListState.Success -> onSuccess()
            }
        })

        viewmodel.allItem.observe(viewLifecycleOwner) { itemList ->
            if (itemList.isNotEmpty()) {
                hideNoDataError()
                itemListAdapter.submitList(itemList)
            } else {
                showNoDataError()
            }
        }
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
        return when (menuItem.itemId) {
            R.id.action_sortTask -> {
                Snackbar.make(
                    requireView(),
                    getString(R.string.snackbar_sort_item_rate),
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Ordenado por item", null).show()
                itemListAdapter.sortPrecio()

                return true
            }

            R.id.action_refreshTask -> {
                Snackbar.make(
                    requireView(),
                    getString(R.string.snackbar_sort_item_name),
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Ordenado por nombre", null).show()
                itemListAdapter.submitList(viewmodel.allItem.value)
                return true
            }

            else -> false
        }
    }

    override fun onStart() {
        super.onStart()
        loadList()
    }

    private fun loadList() {
        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val orderValue = preferences!!.getString("items", "0")


        when (orderValue) {

            "0" -> {
                itemListAdapter.sortPrecio()
            }

            "1" -> {
                viewmodel.getItemList()
            }


        }
    }


    private fun onSuccess() {
        hideNoDataError()
    }

    private fun hideNoDataError() {
        with(binding) {
            lavNoItems.visibility = View.GONE
            tvItemListEmptyTitle.visibility = View.GONE
            tvItemListEmptyText.visibility = View.GONE
            rvItemList.visibility = View.VISIBLE
        }
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
            findNavController().navigate(R.id.action_itemListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun setUpItemRecycler() {
        itemListAdapter =
            ItemListAdapter(
                requireContext(),
                { viewItemDetail(it) },
                { initDeleteFragmentDialog(it) })

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

    private fun initDeleteFragmentDialog(item: Item): Boolean {
        val dialog = BaseFragmentDialog.newInstance(
            "Atención",
            "¿Deseas borrar el artículo?"
        )
        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)
        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            if (viewmodel.delete(item)){
                initNotification(item)
            } else{
                Toast.makeText(requireActivity(),"No se puede eliminar el artículo, referenciado en una factura",Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun initNotification(item: Item) {
        createNotificationChannel(requireContext())
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0,  Intent(),PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val title = getString(R.string.notif_item_delete_title, item.name)
        val textContext = getString(R.string.notif_item_text_context)
        sendNotification(requireContext(),pendingIntent,title, textContext)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}