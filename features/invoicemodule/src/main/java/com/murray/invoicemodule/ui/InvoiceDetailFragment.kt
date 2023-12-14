package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.R
import com.murray.invoicemodule.databinding.FragmentInvoiceDetailBinding
import com.murray.invoicemodule.ui.usecase.InvoiceCreateViewModel
import com.murray.repositories.ItemRepository

class InvoiceDetailFragment : Fragment() {

    private var _binding: FragmentInvoiceDetailBinding? = null
    private val binding get() = _binding!!

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

            val nombreArticulo = binding.invoice!!.articulo!!
            if (!nombreArticulo.isNullOrEmpty()) {
                val partes = nombreArticulo.split(" x ")
                val cont= partes[0]
                val nombre = partes[1]
                val articuloSeleccionado = ItemRepository.getDataSetItem()?.find { it.name.equals(nombre, ignoreCase = true) }

                if (articuloSeleccionado != null) {
                    val precioArticulo = articuloSeleccionado.rate

                    val total: Double = cont.toInt() * precioArticulo
                    binding.txtnArticulo.text = nombre
                    binding.txtptotal.text = "$total €"
                    binding.txtparticulototal.text = "$total €"
                    var impuesto:Double =  0.21 * total
                    var subtotal :Double = total-impuesto
                    binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
                    binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
                    binding.txtparticulo.text = precioArticulo.toString()
                }
            }



        binding.btnEditarEdita.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelable(Invoice.TAG, binding.invoice)

            findNavController().navigate(
                R.id.action_invoiceDetailFragment_to_invoiceCreationFragment,
                bundle
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
