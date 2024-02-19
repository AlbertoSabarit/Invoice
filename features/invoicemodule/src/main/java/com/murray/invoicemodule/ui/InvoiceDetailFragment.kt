package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murray.data.invoices.Invoice
import com.murray.data.tasks.Task
import com.murray.invoicemodule.R
import com.murray.invoicemodule.databinding.FragmentInvoiceDetailBinding
import com.murray.invoicemodule.ui.usecase.InvoiceCreateViewModel

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

        val nombre = StringBuilder()
        val nombreArticulo = binding.invoice!!.lineItems.forEach { lineItem ->
            nombre.append("${lineItem.item.name}")
        }


        //val cont= binding.invoice!!.articulo!!.count
        viewModel.getLineItemList().observe(viewLifecycleOwner) { articulos ->

            val articuloSeleccionado = articulos.find { it.item.name.equals(nombreArticulo) }

            if (articuloSeleccionado != null) {
                val precioArticulo = articuloSeleccionado.price

                val total: Double = articuloSeleccionado.cantidad * precioArticulo
                binding.txtnArticulo.text = nombre
                binding.txtptotal.text = "$total €"
                binding.txtparticulototal.text = "$total €"
                var impuesto: Double = 0.21 * total
                var subtotal: Double = total - impuesto
                binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
                binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
                binding.txtparticulo.text = precioArticulo.toString()
                binding.txtcontArticulo.text = articuloSeleccionado.cantidad.toString() + "x"
            }

        }

        /*val precioArticulo = binding.invoice!!.articulo!!.price

        val total: Double = cont * precioArticulo
        binding.txtnArticulo.text = nombreArticulo
        binding.txtptotal.text = "$total €"
        binding.txtparticulototal.text = "$total €"
        var impuesto:Double =  0.21 * total
        var subtotal :Double = total-impuesto
        binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
        binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
        binding.txtparticulo.text = precioArticulo.toString()
        binding.txtcontArticulo.text = cont.toString() + "x"*/


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
