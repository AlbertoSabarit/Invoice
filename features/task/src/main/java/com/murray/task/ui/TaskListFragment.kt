package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.entities.tasks.Task
import com.murray.repositories.TaskRepository
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
            var bundle = Bundle()
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment, bundle)

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
        taskAdapter = TaskAdapter({viewTask(it)}, {deleteTask(it)})

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = taskAdapter
        }
    }

    private fun deleteTask(task: Task){
        TaskRepository.dataSet.remove(task)

        taskAdapter.update(TaskRepository.dataSet as ArrayList<Task>)

        if(TaskRepository.dataSet.isEmpty()){
            showNoDataError()
        }
    }

    private fun viewTask(task : Task){
        var bundle = Bundle()
        bundle.putParcelable(Task.TAG, task)

        findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
    }
}

