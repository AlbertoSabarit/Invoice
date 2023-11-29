package com.murray.invoicemodule.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import android.app.DatePickerDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.R
import com.murray.invoicemodule.adapter.InvoiceAdapter
import com.murray.invoicemodule.databinding.FragmentInvoiceCreationBinding
import com.murray.repositories.InvoiceRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InvoiceCreationFragment : Fragment() {
    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)

        val nombres = arrayOf("Alberto", "Ender", "Kateryna", "Alejandro")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        /*binding.btnAnadirArt.setOnClickListener {
            findNavController().navigate(R.id.act)
        }*/

        binding.spinner.adapter = adapter

        val cliente = arguments?.getString("cliente") ?: ""
        val fcrear = arguments?.getString("fechacrear") ?: ""
        val fven = arguments?.getString("fechavenc") ?: ""

        val d = binding.spinner.adapter as ArrayAdapter<String>
        val index = d.getPosition(cliente)
        binding.spinner.setSelection(index)
        binding.tiefechaIni.setText(" $fcrear")
        binding.tiefechaFin.setText(" $fven")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tiefechaIni.setOnClickListener {
            showDatePickerIni()
        }
        binding.tiefechaFin.setOnClickListener {
            showDatePickerFin()
        }
    }


    private fun showDatePickerIni() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, day: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.tiefechaIni.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
    private fun showDatePickerFin() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                //val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                //val fechaIniText = binding.tiefechaIni.text.toString()
                //val formattedDate = dateFormat.format(selectedDate.time)
                //val fechaIniDate = dateFormat.parse(fechaIniText)

                /* if (selectedDate.before(fechaIniDate)) {
                     binding.btnGuardarFactura.isEnabled = true
                 } else {
                     binding.btnGuardarFactura.isEnabled = false
                     binding.tiefechaFin.setText(formattedDate)
                 }*/

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.tiefechaFin.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}