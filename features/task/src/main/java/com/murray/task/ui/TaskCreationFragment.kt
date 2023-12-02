package com.murray.task.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.task.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.murray.task.databinding.FragmentTaskCreationBinding
import com.murray.task.ui.usecase.TaskCreateState
import com.murray.task.ui.usecase.TaskCreateViewModel

class TaskCreationFragment : Fragment() {

    private var _binding: FragmentTaskCreationBinding? = null
    private val viewModel: TaskCreateViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)

        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private lateinit var twatcher:LogInTextWatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieFechCreacion.setOnClickListener {
            showDatePickerIni()
        }
        binding.tieFechFin.setOnClickListener {
            showDatePickerFin()
        }

        binding.button.setOnClickListener {
            //findNavController().navigate(R.id.action_taskCreationFragment_to_taskListFragment)
            findNavController().popBackStack()
        }

        twatcher= LogInTextWatcher(binding.tilTitulo)
        binding.tieTitulo.addTextChangedListener(twatcher)

        twatcher= LogInTextWatcher(binding.tilDescripcion)
        binding.tieDescripcion.addTextChangedListener(twatcher)

        viewModel.getState().observe(viewLifecycleOwner, Observer {//importante este metodo que recoge lo de vista/modelo(creo)
            when(it){
                TaskCreateState.TitleEmptyError -> setTitleEmptyError()
                TaskCreateState.DescriptionEmptyError -> setDescriptionEmptyError()
                //is TaskState.AuthenticationError -> showMessage(it.message)
                //is TaskCreateState.Loading -> showProgressbar(it.value)
                else -> onSuccess()
            }
        })

    }

    private fun showProgressbar(value: Boolean) {
        if(value)
            //findNavController().navigate(R.id.action_taskListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
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

                binding.tieFechCreacion.setText(formattedDate)
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

                binding.tieFechFin.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }


    private fun setTitleEmptyError() {
        binding.tilTitulo.error = "Debe escribir un título"
        binding.tilTitulo.requestFocus()
    }

    private fun setDescriptionEmptyError() {
        binding.tilDescripcion.error = "Debe escribir una descripción"
        binding.tilDescripcion.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireActivity(), "Tarea creada", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    /**
     * Creamos una clase interna para acceder a las propiedades sy funciones de la clase externa
     */
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
