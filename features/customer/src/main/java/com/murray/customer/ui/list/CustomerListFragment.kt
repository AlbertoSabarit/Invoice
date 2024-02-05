package com.murray.customer.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.customer.R
import com.murray.customer.databinding.FragmentCustomerListBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.customer.ui.list.usecase.CustomerListState
import com.murray.customer.ui.list.usecase.CustomerListViewModel
import com.murray.data.customers.Customer
import com.murray.invoice.ui.MainActivity

class CustomerListFragment : Fragment(), MenuProvider {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: CustomerListViewModel by viewModels()

    private lateinit var customerAdapter: CustomAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.customerListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment)
        }
        setUpToolbar()
        setUpUserRecycler()


        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                CustomerListState.NoDataError -> showNoDataError()
                CustomerListState.ReferencedCustomer -> showReferencedCustomerError()
                is CustomerListState.Loading -> showProgressBar(it.value)
                CustomerListState.Success -> onSuccess()
            }
        })

        viewmodel.allCustomers.observe(viewLifecycleOwner, Observer { customers ->
            if (customers.isNotEmpty()) {
                hideNoData()
                customerAdapter.submitList(customers)
            } else {
                showNoDataError()
            }
        })
    }

    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_customer_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sortInvoice -> {
                Snackbar.make(requireView(), "Clientes ordenados por email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                customerAdapter.sort()
                return true
            }

            R.id.action_refreshInvoice -> {
                Snackbar.make(requireView(), "Clientes ordenados por nombre", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                customerAdapter.submitList(viewmodel.allCustomers.value)
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

        val orderValue = preferences!!.getString("customers", "0")


        when (orderValue) {
            "0" -> {
                customerAdapter.sort()
            }
            "1" -> {
                viewmodel.getCustomerList()
            }
        }
    }


    private fun showProgressBar(value: Boolean) {
        if (value)
            findNavController().navigate(R.id.action_customerListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun showReferencedCustomerError() {
        findNavController().navigate(R.id.action_customerListFragment_to_baseFragmentDialog)
    }

    private fun showNoDataError() {
        binding.recyclerView.visibility = View.GONE
        binding.lnlSinClientes.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        hideNoData()
    }

    private fun hideNoData() {
        binding.lnlSinClientes.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun setUpUserRecycler() {
        customerAdapter =
            CustomAdapter(requireContext(), { deleteItem(it) }) { customer: Customer ->
                val bundle = bundleOf(
                    "id" to customer.id,
                    "name" to customer.name,
                    "email" to customer.email.getEmail(),
                    "phone" to customer.phone,
                    "city" to customer.city,
                    "address" to customer.address
                )
                findNavController().navigate(
                    R.id.action_customerListFragment_to_customerDetailFragment,
                    bundle
                )
            }
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = customerAdapter
        }
    }

    private fun deleteItem(customer: Customer) {
        viewmodel.deleteItem(customer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}