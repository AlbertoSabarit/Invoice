package com.murray.invoicemodule.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.entities.invoices.Invoice
import com.murray.invoicemodule.databinding.FragmentInvoiceCreationBinding
import com.murray.invoicemodule.ui.usecase.InvoiceCreateState
import com.murray.invoicemodule.ui.usecase.InvoiceCreateViewModel
import com.murray.repositories.CustomerRepository
import com.murray.repositories.InvoiceRepository
import com.murray.repositories.ItemRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InvoiceCreationFragment : Fragment() {
    private var _binding: FragmentInvoiceCreationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InvoiceCreateViewModel by viewModels()
    private lateinit var twatcher: LogInTextWatcher
    private var contadorArt = 1
    private var precioActualArticulo: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInvoiceCreationBinding.inflate(inflater, container, false)

        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        val nombres: MutableList<String> = CustomerRepository.dataSet.map { it.name }.sorted().toMutableList()

        val cliadapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, nombres)
        cliadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = cliadapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cliente = arguments?.getString("cliente") ?: ""
        val fcrear = arguments?.getString("fechacrear") ?: ""
        val fven = arguments?.getString("fechavenc") ?: ""
        val art = arguments?.getString("articulo")?:""

        val d = binding.spinner.adapter as ArrayAdapter<String>
        val index = d.getPosition(cliente)
        binding.spinner.setSelection(index)
        binding.tiefechaIni.setText("$fcrear")
        binding.tiefechaFin.setText("$fven")
        binding.txtnArticulo.setText("$art")


        binding.btnAnadirArt.setOnClickListener{
            binding.cvArticulo.visibility = View.VISIBLE
        }

        binding.tiefechaIni.setOnClickListener {
            showDatePickerIni()
        }
        binding.tiefechaFin.setOnClickListener {
            showDatePickerFin()
        }

        binding.btnGuardarFactura.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imgQuitarArticulo.setOnClickListener{
            binding.txtparticulo.text = ""
            binding.txtptotal.text = ""
            binding.txtnArticulo.text =""
            binding.txtparticulototal.text = ""
            binding.txtpsubtotal.text = ""
            binding.txtpimpuestos.text = ""
            binding.txtnArticulo.text = ""
            binding.contArt.text= ""
            binding.imgQuitarArticulo.visibility = View.GONE
            binding.txtSubtotal.visibility = View.GONE
            binding.txtImpuesto.visibility = View.GONE
            binding.txtTotal.visibility = View.GONE
        }

        twatcher= LogInTextWatcher(binding.tilFechaIni)
        binding.tiefechaIni.addTextChangedListener(twatcher)

        twatcher= LogInTextWatcher(binding.tilFechaFin)
        binding.tiefechaFin.addTextChangedListener(twatcher)

        val narticulos:  MutableList<String> = ItemRepository.getDataSetItem().map { it.name }.sorted().toMutableList()

        val itemadapter = ArrayAdapter(requireContext(),R.layout.simple_spinner_item, narticulos)
        itemadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spArticulos.adapter = itemadapter
        binding.btnMas.setOnClickListener{
            contadorArt++
            binding.contArt.text = contadorArt.toString() + " x "
            updatePrecioTotal()
            itemadapter.notifyDataSetChanged()
        }

        binding.btnMenos.setOnClickListener{
            if(contadorArt>1) {
                contadorArt--
                binding.contArt.text = contadorArt.toString() + " x "
                updatePrecioTotal()
                itemadapter.notifyDataSetChanged()
            }
        }

        binding.spArticulos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selectedArticulo = parentView.getItemAtPosition(position).toString()

                val articuloSeleccionado = ItemRepository.getDataSetItem().find { it.name == selectedArticulo }

                if (articuloSeleccionado != null) {
                    precioActualArticulo = articuloSeleccionado.rate
                    binding.imgQuitarArticulo.visibility = View.VISIBLE
                    binding.txtSubtotal.visibility = View.VISIBLE
                    binding.txtImpuesto.visibility = View.VISIBLE
                    binding.txtTotal.visibility = View.VISIBLE
                    updatePrecioTotal()
                    binding.txtnArticulo.text = articuloSeleccionado.name
                    binding.txtparticulo.text = articuloSeleccionado.rate.toString()

                }
                itemadapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        viewModel.getState().observe(viewLifecycleOwner, Observer {//importante este metodo que recoge lo de vista/modelo(creo)
            when(it){
                InvoiceCreateState.DataIniEmptyError -> setDateIniError()
                InvoiceCreateState.DataFinEmptyError -> setDateFinError()
                InvoiceCreateState.IncorrectDateRangeError -> setDateRangeError()
                else -> onSuccess()
            }
        })
    }
    private fun updatePrecioTotal() {
        val total: Double = contadorArt * precioActualArticulo
        binding.txtptotal.text = "$total €"
        binding.txtparticulototal.text = "$total €"
        var impuesto:Double =  0.21 * total
        var subtotal :Double = total-impuesto
        binding.txtpimpuestos.text = String.format("%.2f €", impuesto)
        binding.txtpsubtotal.text = String.format("%.2f €", subtotal)
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
    private fun setDateIniError() {
        binding.tilFechaIni.error = "Debe elegir una fecha"
        binding.tilFechaIni.requestFocus()
    }

    private fun setDateFinError() {
        binding.tilFechaFin.error = "Debe elegir una fecha"
        binding.tilFechaFin.requestFocus()
    }

    private fun setDateRangeError() {
        binding.tilFechaFin.error = "Error el rango de la fecha "
        binding.tilFechaFin.requestFocus()
    }

    private fun onSuccess() {
        val cliente = binding.spinner.selectedItem.toString()
        val fCreacion = binding.tiefechaIni.text.toString()
        val fVencimiento = binding.tiefechaFin.text.toString()
        val articulo = binding.txtnArticulo.text.toString()
        val artcont = "$contadorArt x $articulo"
        val nuevaFactura = Invoice(cliente, artcont, fCreacion, fVencimiento)

        InvoiceRepository.addInvoice(nuevaFactura)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    open inner class LogInTextWatcher(var tilError: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
        override fun afterTextChanged(s: Editable?) {
            tilError.error = null
        }
    }
}