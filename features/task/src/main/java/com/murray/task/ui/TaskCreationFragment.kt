package com.murray.task.ui

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.entities.customers.Customer
import com.murray.entities.tasks.Task
import com.murray.repositories.CustomerRepository
import com.murray.repositories.TaskRepository
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

    private lateinit var twatcher: LogInTextWatcher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCreationBinding.inflate(inflater, container, false)



        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this


        if (requireArguments().containsKey(Task.TAG)) {
            val task: Task? = requireArguments().getParcelable(Task.TAG)
            viewModel.title.value = task!!.titulo
            viewModel.description.value = task.descripcion
            viewModel.fini.value = task.fechaCreacion
            viewModel.ffin.value = task.fechaFin
            viewModel.task = task
        } else {
            viewModel.task = Task.createDefaultTask()
        }

        initSpinnerClientes()
        initSpinnerTipo()
        initSpinnerEstado()

        return binding.root
    }

    private fun initSpinnerClientes() {
        val nombres: MutableList<String> = CustomerRepository.dataSet.map { it.name }.sorted().toMutableList()

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spTaskClientes.adapter = adapter

        if (viewModel.task.id != -1) {
            var pos = nombres.indexOf(viewModel.task.nombre)

            binding.spTaskClientes.setSelection(pos, false)
        }
    }

    private fun initSpinnerTipo() {
        val tipos: Array<String> = arrayOf("Privado", "Llamada", "Visita")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapter

        if (viewModel.task.id != -1) {
            var pos = tipos.indexOf(viewModel.task.tarea)

            binding.spinnerTipo.setSelection(pos, false)
        }
    }

    private fun initSpinnerEstado() {
        val estados: Array<String> = arrayOf("Pendiente", "Modificado", "Vencido")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerEstado.adapter = adapter

        if (viewModel.task.id != -1) {
            var pos = estados.indexOf(viewModel.task.estado)

            binding.spinnerEstado.setSelection(pos, false)
        }
    }


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

        twatcher = LogInTextWatcher(binding.tilTitulo)
        binding.tieTitulo.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilDescripcion)
        binding.tieDescripcion.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilFechaIni)
        binding.tieFechCreacion.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilFechaFin)
        binding.tieFechFin.addTextChangedListener(twatcher)


        viewModel.getState().observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    TaskCreateState.TitleEmptyError -> setTitleEmptyError()
                    TaskCreateState.DescriptionEmptyError -> setDescriptionEmptyError()
                    TaskCreateState.DataIniEmptyError -> setDateIniError()
                    TaskCreateState.DataFinEmptyError -> setDateFinError()
                    TaskCreateState.IncorrectDateRangeError -> setDateRangeError()
                    is TaskCreateState.Loading -> {}
                    is TaskCreateState.TaskCreateError -> setErrorCreateTask()

                    else -> onSuccess()
                }
            })

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

    private fun setErrorCreateTask() {
        Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
    }

    private fun setTitleEmptyError() {
        binding.tilTitulo.error = "Debe escribir un título"
        binding.tilTitulo.requestFocus()
    }

    private fun setDescriptionEmptyError() {
        binding.tilDescripcion.error = "Debe escribir una descripción"
        binding.tilDescripcion.requestFocus()
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
        binding.tilFechaFin.error = "Error de rango de la fecha"
        binding.tilFechaFin.requestFocus()
    }

    private fun onSuccess() {

        if (viewModel.task.id == -1) {
            TaskRepository.addTask(
                Task(
                    Task.lastId++,
                    binding.tieTitulo.text.toString(),
                    binding.spTaskClientes.selectedItem.toString(),
                    binding.spinnerTipo.selectedItem.toString(),
                    binding.tieFechCreacion.text.toString(),
                    binding.tieFechFin.text.toString(),
                    binding.spinnerEstado.selectedItem.toString(),
                    binding.tieDescripcion.text.toString()
                )
            )
            Toast.makeText(requireActivity(), "Tarea creada", Toast.LENGTH_SHORT).show()
        } else {

            for (task in TaskRepository.dataSet) {
                if (viewModel.task.id == task.id) {

                    task.titulo = binding.tieTitulo.text.toString()
                    task.nombre = binding.spTaskClientes.selectedItem.toString()
                    task.tarea = binding.spinnerTipo.selectedItem.toString()
                    task.fechaCreacion = binding.tieFechCreacion.text.toString()
                    task.fechaFin = binding.tieFechFin.text.toString()
                    task.estado = binding.spinnerEstado.selectedItem.toString()
                    task.descripcion = binding.tieDescripcion.text.toString()
                }
            }

            Toast.makeText(requireActivity(), "Tarea editada", Toast.LENGTH_SHORT).show()
        }

        findNavController().popBackStack()
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
