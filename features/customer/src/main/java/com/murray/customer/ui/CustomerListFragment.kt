package com.murray.customer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.customer.R
import com.murray.customer.databinding.FragmentCustomerListBinding
import androidx.navigation.fragment.findNavController
import com.murray.customer.ui.adapter.CustomAdapter
import com.murray.customer.ui.data.model.Customer
import com.murray.customer.ui.data.repository.CustomerRepository

class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

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
        setUpUserRecycler()
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_customerListFragment_to_customerCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpUserRecycler(){
        var adapter = CustomAdapter (CustomerRepository.dataSet, requireContext()) { _: Customer ->
            findNavController().navigate(R.id.action_customerListFragment_to_customerDetailFragment)
        }
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }
}