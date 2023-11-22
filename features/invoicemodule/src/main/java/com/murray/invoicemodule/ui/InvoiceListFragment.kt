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

        binding.btnCrearFactura.setOnClickListener {
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment)
        }
    }

    private fun setUpUserRecycler() {
        var adapter: InvoiceAdapter = InvoiceAdapter(com.murray.repositories.InvoiceRepository.dataSet, requireContext())

        adapter.setOnItemClickListener(object : InvoiceAdapter.OnItemClickListener {
            override fun onItemClick(item: com.murray.entities.invoices.Invoice) {
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