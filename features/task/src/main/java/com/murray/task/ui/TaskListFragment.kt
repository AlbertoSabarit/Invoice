package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.tasks.Task
import com.murray.task.R
import com.murray.task.adapter.TaskAdapter
import com.murray.repositories.TaskRepository
import com.murray.task.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(){

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTaskRecycler()
        binding.btnCreateTask.setOnClickListener {
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment)

        }
    }


    private fun setUpTaskRecycler() {
        var adapter = TaskAdapter (TaskRepository.dataSet, requireContext()) { task: Task ->
            val bundle = bundleOf(
                "titulo" to task.titulo,
                "cliente" to task.nombre,
                "tarea" to task.tarea,
                "estado" to task.estado,
                "descripcion" to task.descripcion
            )
            findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
        }

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}

