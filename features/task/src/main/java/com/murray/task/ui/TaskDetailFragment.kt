package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        val titulo = arguments?.getString("titulo") ?: ""
        val cliente = arguments?.getString("cliente") ?: ""
        val tarea = arguments?.getString("tarea") ?: ""
        val estado = arguments?.getString("estado") ?: ""
        val descripcion = arguments?.getString("descripcion") ?: ""
        binding.tvTaskTitle.text = titulo
        binding.tvclientetask.text = cliente
        binding.tvTaskTipo.text = tarea
        binding.tvTaskEstado.text = estado
        binding.tvTaskDescripcion.text = descripcion

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}