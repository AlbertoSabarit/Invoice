package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.task.R
import com.murray.task.adapter.TaskAdapter
import com.murray.repositories.TaskRepository
import com.murray.task.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(), TaskAdapter.OnItemClickListener {

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

    override fun onItemClick(position: Int) {
        findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment)
    }

    private fun setUpTaskRecycler() {
        val adapter = TaskAdapter(com.murray.repositories.TaskRepository.dataSet, requireContext(), this)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}

