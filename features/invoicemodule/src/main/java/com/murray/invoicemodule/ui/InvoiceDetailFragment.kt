package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.murray.invoicemodule.databinding.FragmentInvoiceDetailBinding
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

        val cliente = arguments?.getString("cliente") ?: ""
        val fcrear = arguments?.getString("fechacrear") ?: ""
        val fven = arguments?.getString("fechavenc") ?: ""
        val art = arguments?.getString("articulo") ?: ""
        val partes = art.split(" x ")
        val nombre = partes[1]
        val cont:Int  = partes[0].toInt()

        binding.txtCliente.text = "$cliente"
        binding.txtFechaCreacion.text = " $fcrear"
        binding.txtFechaVenc.text = " $fven"
        binding.txtnArticulo.text = "$nombre"
        binding.txtcontArticulo.text = "$cont x"

        val articuloSeleccionado = ItemRepository.getDataSetItem().find { it.name == nombre }

        if (articuloSeleccionado != null) {
            val precioArticulo = articuloSeleccionado.rate

            val total: Double = cont * precioArticulo
            binding.txtptotal.text = "$total €"
            binding.txtparticulototal.text = "$total €"
            var impuesto:Double =  0.21 * total
            var subtotal :Double = total-impuesto
            binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
            binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
            binding.txtparticulo.text = precioArticulo.toString()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}