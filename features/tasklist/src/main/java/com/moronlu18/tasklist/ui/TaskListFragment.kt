package com.moronlu18.tasklist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moronlu18.tasklist.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.tasklist.databinding.FragmentTaskListBinding


class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("Posicion 1", "Posicion 2", "Posicion 3", "Posicion 4", "Posicion 5","Posicion 1", "Posicion 2", "Posicion 3", "Posicion 4", "Posicion 4", "Posicion 5","Posicion 1", "Posicion 2", "Posicion 3", "Posicion 4")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AdapterPersonalizado(items)


        binding.btnCreateTask.setOnClickListener{
            //findNavController().navigate()
            findNavController().navigate(com.moronlu18.invoice.R.id.action_taskListFragment_to_taskCreationFragment3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}