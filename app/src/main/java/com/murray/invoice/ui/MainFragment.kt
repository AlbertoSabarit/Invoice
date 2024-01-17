package com.murray.invoice.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murray.invoice.R
import com.murray.invoice.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)

        val orderValue2 = preferences!!.getString("modo", "0")
        when (orderValue2) {

                "0" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                "1" -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSignin.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_account)
        }

        binding.btCustomerList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_customer)
        }

        binding.btInvoiceList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_invoice)
        }
        binding.btItemList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_item)
        }

        binding.btTaskList.setOnClickListener {
            //findNavController().navigate()
            findNavController().navigate(R.id.action_mainFragment_to_nav_graph_task)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}