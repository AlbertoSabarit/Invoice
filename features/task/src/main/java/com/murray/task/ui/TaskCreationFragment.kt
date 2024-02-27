package com.murray.task.ui

import com.murray.task.R
//import android.R
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
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
import com.hanmajid.android.tiramisu.notificationruntimepermission.createNotificationChannel
import com.hanmajid.android.tiramisu.notificationruntimepermission.sendNotification
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task
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

    private var taskTmp: Task? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieFechCreacion.setOnClickListener {
            showDatePickerIni()
        }
        binding.tieFechFin.setOnClickListener {
            showDatePickerFin()
        }

        twatcher = LogInTextWatcher(binding.tilTitulo)
        binding.tieTitulo.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilDescripcion)
        binding.tieDescripcion.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilFechaIni)
        binding.tieFechCreacion.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tilFechaFin)
        binding.tieFechFin.addTextChangedListener(twatcher)


        binding.button.setOnClickListener {

            var cliente = Customer()

            viewModel.getCustomerList().observe(viewLifecycleOwner) { customers ->

                for (c in customers) {
                    if (c.name == binding.spTaskClientes.selectedItem.toString()) {
                        cliente = c
                        break
                    }
                }

                taskTmp = Task(
                    binding.tieTitulo.text.toString(),
                    cliente,
                    binding.spinnerTipo.selectedItem.toString(),
                    binding.tieFechCreacion.text.toString(),
                    binding.tieFechFin.text.toString(),
                    binding.spinnerEstado.selectedItem.toString(),
                    binding.tieDescripcion.text.toString()
                )

                if (viewModel.task.id == -1) {
                    initNotification("Tarea creada")
                    viewModel.validateCredentials(taskTmp!!)
                } else {
                    initNotification("Tarea editada")
                    taskTmp!!.id = viewModel.task.id
                    viewModel.validateCredentials(taskTmp!!)
                }
            }
        }


        viewModel.getState().observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    TaskCreateState.TitleEmptyError -> setTitleEmptyError()
                    TaskCreateState.DescriptionEmptyError -> setDescriptionEmptyError()
                    TaskCreateState.DataIniEmptyError -> setDateIniError()
                    TaskCreateState.DataFinEmptyError -> setDateFinError()
                    TaskCreateState.IncorrectDateRangeError -> setDateRangeError()
                    is TaskCreateState.TaskError -> setTaskError()
                    is TaskCreateState.Loading -> onLoading(it.value)
                    is TaskCreateState.TaskCreateError -> setErrorCreateTask()

                    else -> onSuccess()
                }
            })
    }

    private fun initNotification(title : String) {
        createNotificationChannel(requireContext())
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0,  Intent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val textContext = "Acción realizada con éxito"
        sendNotification(requireContext(),pendingIntent,title, textContext)
    }

    private fun initSpinnerClientes() {
        viewModel.getCustomerList().observe(viewLifecycleOwner) { customers ->
            val nombres: MutableList<String> = customers.map { it.name }.sorted().toMutableList()

            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTaskClientes.adapter = adapter

            if (viewModel.task.id != -1) {
                val clienteName = viewModel.task.cliente.name
                val pos = nombres.indexOf(clienteName)
                if (pos != -1) {
                    binding.spTaskClientes.setSelection(pos, false)
                } else {

                }
            }
        }
    }


    private fun initSpinnerTipo() {
        val tipos: Array<String> = arrayOf("Privado", "Llamada", "Visita")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapter

        if (viewModel.task.id != -1) {
            var pos = tipos.indexOf(viewModel.task.tipoTarea)

            binding.spinnerTipo.setSelection(pos, false)
        }
    }

    private fun initSpinnerEstado() {
        val estados: Array<String> = arrayOf("Pendiente", "Modificado", "Vencido")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEstado.adapter = adapter

        if (viewModel.task.id != -1) {
            var pos = estados.indexOf(viewModel.task.estado)

            binding.spinnerEstado.setSelection(pos, false)
        }
    }

    private fun onLoading(value: Boolean) {

        if (value) {
            findNavController().navigate(R.id.action_taskCreationFragment_to_fragmentProgressDialog)
        } else {
            findNavController().popBackStack()
        }
    }

    private fun setTaskError() {
        //binding.tilTitulo.error = "Ya existe una tarea con ese título"
        Toast.makeText(context, "Cliente erroneo o inexistente", Toast.LENGTH_SHORT).show()
        binding.tilTitulo.requestFocus()
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

        val bundle = Bundle()
        bundle.putParcelable(Task.TAG, taskTmp)
        parentFragmentManager.setFragmentResult("editTaskResult", bundle)

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
