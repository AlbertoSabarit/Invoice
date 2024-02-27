package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.murray.data.invoices.Invoice
import com.murray.invoicemodule.R
import com.murray.invoicemodule.databinding.FragmentInvoiceDetailBinding
import com.murray.invoicemodule.ui.usecase.InvoiceCreateViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InvoiceDetailFragment : Fragment() {

    private var _binding: FragmentInvoiceDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InvoiceCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.invoice = requireArguments().getParcelable(Invoice.TAG)

        viewModel.getLineItemsForInvoice(binding.invoice!!.id).onEach { lineItems ->
            val nombreArticulo = StringBuilder()
            lineItems.forEach { lineItem ->
                nombreArticulo.append("${lineItem.item.name}")
            }
            val nombreArticuloString = nombreArticulo.toString()

            val precioTotal = lineItems.sumByDouble { it.item.rate * it.cantidad }
            binding.txtnArticulo.text = nombreArticuloString
            binding.txtptotal.text = "$precioTotal €"
            binding.txtparticulototal.text = "$precioTotal €"
            val impuesto = 0.21 * precioTotal
            val subtotal = precioTotal - impuesto
            binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
            binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
            binding.txtparticulo.text = lineItems.firstOrNull()?.item?.rate.toString()
            binding.txtcontArticulo.text = lineItems.sumBy { it.cantidad }.toString() + "x"
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.btnEditarEdita.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelable(Invoice.TAG, binding.invoice)

            findNavController().navigate(
                R.id.action_invoiceDetailFragment_to_invoiceCreationFragment,
                bundle
            )
        }

        parentFragmentManager.setFragmentResultListener(
            "editInvoiceResult",
            viewLifecycleOwner
        ) { _, bundle ->
            val updatedInvoice = bundle.getParcelable<Invoice>(Invoice.TAG)
            binding.invoice = updatedInvoice
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
