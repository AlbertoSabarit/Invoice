package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.tasks.Task
import com.murray.task.R
import com.murray.task.adapter.TaskAdapter
import com.murray.task.databinding.FragmentTaskListBinding
import com.murray.task.ui.usecase.TaskListState
import com.murray.task.ui.usecase.TaskListViewModel

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: TaskListViewModel by viewModels()

    private lateinit var taskAdapter: TaskAdapter

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

        viewmodel.getState().observe(viewLifecycleOwner, Observer{
            when(it){
                is TaskListState.Loading -> {} //showProgressBar(it.value)
                TaskListState.NoDataError -> showNoDataError()
                is TaskListState.Success -> onSuccess(it.dataset)
            }
        })


    }

    override fun onStart() {
        super.onStart()
        viewmodel.getTaskList()
    }

    private fun onSuccess(dataset: ArrayList<Task>){
        //Desactivar la animacion y visualizar el recyclerview
        hideNoDataError()
        taskAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.lnlSinTareas.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }


    /**
     * Funcion que muestra el error de no hay datos
     */
    private fun showNoDataError(){
        binding.lnlSinTareas.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun showProgressBar(value : Boolean){
        if(value)
            findNavController().navigate(R.id.action_taskListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun setUpTaskRecycler() {
        taskAdapter = TaskAdapter() { task: Task ->
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
            this.adapter = taskAdapter
        }
    }

}

