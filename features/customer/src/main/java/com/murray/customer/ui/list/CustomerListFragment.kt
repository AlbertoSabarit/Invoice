package com.murray.customer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.customer.R
import com.murray.customer.databinding.FragmentCustomerListBinding
import androidx.navigation.fragment.findNavController
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.customer.ui.list.usecase.CustomerListState
import com.murray.customer.ui.list.usecase.CustomerListViewModel
import com.murray.entities.customers.Customer
import com.murray.repositories.CustomerRepository

class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    private val customerlistviewmodel :CustomerListViewModel by viewModels()

    private lateinit var customerAdapter:CustomAdapter

    override fun onStart(){
        super.onStart()
        customerlistviewmodel.getCustomerList()
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

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment)
        }

        customerlistviewmodel.getState().observe(viewLifecycleOwner){
            when(it){
                CustomerListState.NoDataError -> showNoDataError()
                is CustomerListState.Success -> onSuccess(it.dataset)
            }
        }
    }

    private fun showNoDataError() {
        binding.recyclerView.visibility = View.GONE
        binding.lnlSinClientes.visibility = View.VISIBLE
    }

    private fun onSuccess(dataset: ArrayList<Customer>) {
        hideNoData()
        setUpUserRecycler()
        customerAdapter.update(dataset)
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
        customerAdapter = CustomAdapter (requireContext(), {deleteItem(it)}) { customer: Customer ->
            val bundle = bundleOf(
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
            setHasFixedSize(true)
            this.adapter=customerAdapter
        }
    }
    private fun deleteItem(customer: Customer) {
        CustomerRepository.getCustomers().remove(customer)
        customerAdapter.update(CustomerRepository.getCustomers() as ArrayList<Customer>)
        if (CustomerRepository.getCustomers().isEmpty()) {
            showNoDataError()
        }
    }
}