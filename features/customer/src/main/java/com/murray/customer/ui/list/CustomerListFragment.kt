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

    private val customerlistviewmodel :CustomerListViewModel by viewModels()

    private lateinit var customerAdapter:CustomAdapter

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
        return when(menuItem.itemId){
            R.id.action_sortInvoice ->{
                customerAdapter.sort()
                return true
            }
            R.id.action_refreshInvoice ->{
                customerlistviewmodel.getCustomerListOrderByName()
                return true
            }
            else-> false
        }
    }

    override fun onStart() {
        super.onStart()
        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val orderValue = preferences!!.getString("customers", "0")


        when (orderValue) {
            "0" -> {
                customerlistviewmodel.getCustomerListOrderByName()
                Snackbar.make(requireView(), "Clientes ordenados por nombre", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            "1" -> {
                customerlistviewmodel.getCustomerListOrderByEmail()
                Snackbar.make(requireView(), "Clientes ordenados por email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment)
        }
        setUpToolbar()

        setUpUserRecycler()

        customerlistviewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                CustomerListState.NoDataError -> showNoDataError()
                is CustomerListState.Success -> onSuccess()
                CustomerListState.ReferencedCustomer -> showReferencedCustomerError()
                is CustomerListState.Loading -> showProgressBar(it.value)
            }
        }
    }
    private fun showProgressBar(value : Boolean){
        if(value)
        findNavController().navigate(R.id.action_customerListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun showReferencedCustomerError(){
        findNavController().navigate(R.id.action_customerListFragment_to_baseFragmentDialog)
    }

    private fun showNoDataError() {
        binding.recyclerView.visibility = View.GONE
        binding.lnlSinClientes.visibility = View.VISIBLE
    }

    private fun onSuccess() {
        hideNoData()
    }
    private fun hideNoData(){
        binding.lnlSinClientes.visibility=View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpUserRecycler(){
        customerAdapter = CustomAdapter ({deleteItem(it)}) { customer: Customer ->
            val bundle = bundleOf(
                "id" to customer.id,
                "name" to customer.name,
                "email" to customer.email.getEmail(),
                "phone" to customer.phone,
                "city" to customer.city,
                "address" to customer.address
            )
            findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment, bundle)
        }
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            //setHasFixedSize(true)
            this.adapter=customerAdapter
        }
    }
    private fun deleteItem(customer: Customer) {
        customerlistviewmodel.delete(customer)
        showNoDataError()
    }
}