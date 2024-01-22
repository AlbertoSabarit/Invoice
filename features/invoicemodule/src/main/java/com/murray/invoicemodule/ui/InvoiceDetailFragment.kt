package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.R
import com.murray.invoicemodule.databinding.FragmentInvoiceDetailBinding

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

        val nombreArticulo = binding.invoice!!.articulo!!.item.name


        val cont= binding.invoice!!.articulo!!.count

        /* val articuloSeleccionado = ItemRepository.getDataSetItem()?.find { it.name.equals(nombreArticulo) }

         if (articuloSeleccionado != null) {
             val precioArticulo = articuloSeleccionado.rate

             val total: Double = cont * precioArticulo
             binding.txtnArticulo.text = nombreArticulo
             binding.txtptotal.text = "$total €"
             binding.txtparticulototal.text = "$total €"
             var impuesto:Double =  0.21 * total
             var subtotal :Double = total-impuesto
             binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
             binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
             binding.txtparticulo.text = precioArticulo.toString()
             binding.txtcontArticulo.text = cont.toString() + "x"
         }*/



        val precioArticulo = binding.invoice!!.articulo!!.price

        val total: Double = cont * precioArticulo
        binding.txtnArticulo.text = nombreArticulo
        binding.txtptotal.text = "$total €"
        binding.txtparticulototal.text = "$total €"
        var impuesto:Double =  0.21 * total
        var subtotal :Double = total-impuesto
        binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
        binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
        binding.txtparticulo.text = precioArticulo.toString()
        binding.txtcontArticulo.text = cont.toString() + "x"


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
