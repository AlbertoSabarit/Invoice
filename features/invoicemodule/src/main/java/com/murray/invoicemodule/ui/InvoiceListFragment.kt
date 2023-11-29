package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.murray.invoicemodule.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.invoicemodule.adapter.InvoiceAdapter
import com.murray.entities.invoices.Invoice
import com.murray.repositories.InvoiceRepository
import com.murray.invoicemodule.databinding.FragmentInvoiceListBinding

class InvoiceListFragment : Fragment() {

    private var _binding: FragmentInvoiceListBinding? = null
    private val binding get() = _binding!!
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

        binding.btnCrearFactura.setOnClickListener {
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }

        var adapter: InvoiceAdapter = InvoiceAdapter(InvoiceRepository.dataSet, requireContext())
        if (adapter.itemCount == 0) {
            binding.lnlSinFactura.visibility = View.VISIBLE
        } else {
            binding.lnlSinFactura.visibility = View.INVISIBLE
        }


    }

    private fun setUpUserRecycler() {
        var adapter: InvoiceAdapter = InvoiceAdapter(InvoiceRepository.dataSet, requireContext())

        adapter.setOnItemClickListener(object : InvoiceAdapter.OnItemClickListener {
            override fun onItemClick(item:Invoice) {
                val bundle = bundleOf(
                    "cliente" to item.cliente,
                    "fechacrear" to item.fcreacion,
                    "fechavenc" to item.fvencimiento,
                    "articulo" to item.articulo
                )
                findNavController().navigate(
                    R.id.action_invoiceListFragment_to_invoiceDetailFragment,
                    bundle
                )
            }
        })


        if (adapter.itemCount == 0) {
            binding.lnlSinFactura.visibility = View.VISIBLE
        } else {
            binding.lnlSinFactura.visibility = View.INVISIBLE
        }

        with(binding.invoicerv) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}