package com.murray.invoicelist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.invoicelist.R
import com.murray.invoicelist.data.model.Invoice
import com.murray.invoicelist.databinding.FragmentInvoiceListBinding

class InvoiceListFragment : Fragment() {

    private var _binding: FragmentInvoiceListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUserRecycler()

        binding.btnCrearFactura.setOnClickListener{
            findNavController().navigate(com.murray.invoice.R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }
    }

    private fun setUpUserRecycler(){
        var adapter:ListaAdapter = ListaAdapter(setUpDataSetUser(), requireContext())
        with(binding.invoicerv){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun setUpDataSetUser():MutableList<Invoice>{
        var dataSet: MutableList<Invoice> = ArrayList()
        dataSet.add(Invoice("Katya Nikitenko", "Ferrari SD95", "23/07/2023", "12/05/2024"))
        dataSet.add(Invoice("Eliseo", "Audi Q7", "09/03/2024", "12/05/2024"))
        dataSet.add(Invoice("Automoviles Nieto", "Ferrari SF90", "21/10/2024", "31/10/2024"))
        dataSet.add(Invoice("Antonio", "BMW ", "12/05/2023", "12/04/2026"))
        dataSet.add(Invoice("Carlos", "Mercedes", "12/12/2023", "01/05/2024"))
        return dataSet
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}