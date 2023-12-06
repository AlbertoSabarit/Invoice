package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.murray.invoicemodule.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.invoicemodule.adapter.InvoiceAdapter
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.adapter.InvoiceAdapter.OnEditClickListener
import com.murray.invoicemodule.adapter.InvoiceViewHolder
import com.murray.invoicemodule.databinding.FragmentInvoiceListBinding
import com.murray.invoicemodule.ui.usecase.InvoiceListState
import com.murray.invoicemodule.ui.usecase.InvoiceListViewModel
import com.murray.repositories.InvoiceRepository

class InvoiceListFragment : Fragment(), OnEditClickListener{

    private var _binding: FragmentInvoiceListBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: InvoiceListViewModel by viewModels()
    private lateinit var invoiceAdapter: InvoiceAdapter
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

        viewmodel.getState().observe(viewLifecycleOwner, Observer{
            when(it){
                is InvoiceListState.Loading -> showProgressBar(it.value)
                InvoiceListState.NoDataError -> showNoDataError()
                is InvoiceListState.Success -> onSuccess(it.dataset)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        viewmodel.getInvocieList()
    }
    private fun setUpUserRecycler() {
        invoiceAdapter = InvoiceAdapter(requireContext())
        invoiceAdapter.setOnItemClickListener(object : InvoiceAdapter.OnItemClickListener {
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
        invoiceAdapter.setOnEditClickListener(object : OnEditClickListener {
            override fun onEditClick(item: Invoice) {
                //InvoiceRepository.dataSet.remove(item)
                val bundle = bundleOf(
                    "cliente" to item.cliente,
                    "fechacrear" to item.fcreacion,
                    "fechavenc" to item.fvencimiento,
                    "articulo" to item.articulo,
                )
                findNavController().navigate(
                    R.id.action_invoiceListFragment_to_invoiceCreationFragment,
                    bundle
                )

                invoiceAdapter.notifyDataSetChanged()
            }
        })

        invoiceAdapter.setOnDeleteClickListener(object : InvoiceViewHolder.OnDeleteClickListener {
            override fun onDeleteClick(item: Invoice) {
                InvoiceRepository.dataSet.remove(item)
                if (invoiceAdapter.itemCount == 0){
                    binding.lnlSinFactura.visibility = View.VISIBLE
                }
                invoiceAdapter.notifyDataSetChanged()
            }
        })

        with(binding.invoicerv) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = invoiceAdapter
        }
    }

    private fun onSuccess(dataset: ArrayList<Invoice>){
        hideNoDataError()
        invoiceAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.lnlSinFactura.visibility = View.GONE
        binding.invoicerv.visibility = View.VISIBLE
    }
    private fun showNoDataError(){
        binding.lnlSinFactura.visibility = View.VISIBLE
        binding.invoicerv.visibility = View.GONE
    }
    private fun showProgressBar(value : Boolean){
        if(value)
            findNavController().navigate(R.id.action_invoiceListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditClick(item: Invoice) {
        TODO("Not yet implemented")
    }

}
