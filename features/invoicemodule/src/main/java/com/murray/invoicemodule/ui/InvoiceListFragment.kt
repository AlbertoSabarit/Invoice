package com.murray.invoicemodule.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.murray.invoicemodule.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.murray.data.invoices.Invoice
import com.murray.invoicemodule.adapter.InvoiceAdapter
import com.murray.invoice.ui.MainActivity
import com.murray.invoice.base.BaseFragmentDialog
import com.murray.invoicemodule.databinding.FragmentInvoiceListBinding
import com.murray.invoicemodule.ui.usecase.InvoiceListState
import com.murray.invoicemodule.ui.usecase.InvoiceListViewModel

class InvoiceListFragment : Fragment(), InvoiceAdapter.onInvoiceClick, MenuProvider {

    private var _binding: FragmentInvoiceListBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: InvoiceListViewModel by viewModels()

    private lateinit var invoiceAdapter: InvoiceAdapter

    val TAG = "ListInvoice"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.invoiceListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )
        setUpToolbar()
        setUpUserRecycler()

        binding.btnCrearFactura.setOnClickListener {
            var bundle = Bundle()
            findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceCreationFragment, bundle)
        }

        viewmodel.getState().observe(viewLifecycleOwner, Observer{
            when(it){
                is InvoiceListState.Loading -> showProgressBar(it.value)
                InvoiceListState.NoDataError -> showNoDataError()
                is InvoiceListState.Success -> onSuccess(it.dataset)
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
        menuInflater.inflate(R.menu.menu_invoice_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.action_sortInvoice ->{
                invoiceAdapter.sort()
                return true
            }
            R.id.action_refreshInvoice ->{
                viewmodel.getInvoiceListOrderByCustomer()
                return true
            }
            else-> false
        }
    }

    override fun onStart() {
        super.onStart()
        //viewmodel.getInvocieList()

        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val orderValue = preferences!!.getString("invoices", "0")


        when (orderValue) {
            "0" -> {
                viewmodel.getInvoiceListOrderByItem()
                Snackbar.make(requireView(), "Factura ordenada por articulo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            "1" -> {
                viewmodel.getInvoiceListOrderByCustomer()
                Snackbar.make(requireView(), "Factura ordenada por cliente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }


    }
    private fun setUpUserRecycler() {
        invoiceAdapter = InvoiceAdapter(this)
        with(binding.invoicerv) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = invoiceAdapter
        }
    }

    private fun onSuccess(dataset: ArrayList<Invoice>){
        hideNoDataError()
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

    override fun clickListener(invoice: Invoice) {
        var bundle = Bundle()
        bundle.putParcelable(Invoice.TAG, invoice)

        findNavController().navigate(R.id.action_invoiceListFragment_to_invoiceDetailFragment, bundle)
    }

    override fun userOnLongClickDelete(invoice: Invoice) : Boolean {

        val dialog = BaseFragmentDialog.newInstance("Atención",
            "¿Estás seguro de que deseas borrar esta factura?"
        )

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)

        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) {_, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                viewmodel.removeFromList(invoice)
                viewmodel.getInvocieList()
            }
        }
        return true
    }

}


