package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murray.entities.tasks.Task
import com.murray.task.R
import com.murray.task.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("id") ?: ""
        val titulo = arguments?.getString("titulo") ?: ""
        val cliente = arguments?.getString("cliente") ?: ""
        val tarea = arguments?.getString("tarea") ?: ""
        val estado = arguments?.getString("estado") ?: ""
        val descripcion = arguments?.getString("descripcion") ?: ""
        val fechaCreacion = arguments?.getString("fechaCreacion") ?: ""
        val fechaFin = arguments?.getString("fechaFin") ?: ""

        var task = Task(
            id.toString().toInt(),
            titulo,
            cliente,
            tarea,
            fechaCreacion,
            fechaFin,
            estado,
            descripcion
        )

        binding.btnEditar.setOnClickListener {

            var bundle = Bundle()
            bundle.putParcelable(Task.TAG, task)

            findNavController().navigate(
                R.id.action_taskDetailFragment_to_taskCreationFragment,
                bundle
            )
        }



        binding.tvTaskTitle.text = "$titulo"
        binding.tvclientetask.text = "$cliente"
        binding.tvTaskTipo.text = "$tarea"
        binding.tvTaskEstado.text = "$estado"
        binding.tvTaskDescripcion.text = "$descripcion"
        binding.fechCreacion.text = "$fechaCreacion"
        binding.fechFin.text = "$fechaFin"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}