package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val cliente = arguments?.getString("cliente") ?: ""
        val fcrear = arguments?.getString("fechacrear") ?: ""
        val fven = arguments?.getString("fechavenc") ?: ""

        binding.txtCliente.text = "$cliente"
        binding.txtFechaCreacion.text = " $fcrear"
        binding.txtFechaVenc.text = " $fven"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}