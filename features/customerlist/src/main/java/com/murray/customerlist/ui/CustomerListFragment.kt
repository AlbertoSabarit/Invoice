package com.murray.customerlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.customerlist.R
import com.murray.customerlist.databinding.FragmentCustomerListBinding

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
            //findNavController().navigate()
            findNavController().navigate(com.murray.invoice.R.id.action_customerListFragment_to_customerCreationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpUserRecycler(){
        var adapter: CustomAdapter = CustomAdapter (getUpDataSetUser(), requireContext())
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }
    private fun getUpDataSetUser(): MutableList<Customer> {
        var dataset: MutableList<Customer> = ArrayList()
        dataset.add(Customer("Alejandro Valle", "alejandro@gmail.es"))
        dataset.add(Customer("Alberto Sabarit", "alberto@gmail.es"))
        dataset.add(Customer("Ender Watts", "ender@gmail.uk"))
        dataset.add(Customer("Katya Nikitenko", "katya@gmail.ua"))
        dataset.add(Customer("Lourdes Rodriguez", "Lourdes@gmail.com"))
        dataset.add(Customer("Carlos Cortijo", "Carlos@gmail.com"))
        dataset.add(Customer("Federico Huercano", "Federico@gmail.com"))
        dataset.add(Customer("Francisco Garcia", "Francisco@gmail.com"))
        dataset.add(Customer("Eliseo Moreno", "Eliseo@gmail.com"))
        dataset.add(Customer("Francisco Cabrera", "Francisco@gmail.com"))
        dataset.add(Customer("Jose Millan", "Jose@gmail.com"))
        dataset.add(Customer("Pello Mir", "Pello@gmail.com"))
        return dataset
    }

}