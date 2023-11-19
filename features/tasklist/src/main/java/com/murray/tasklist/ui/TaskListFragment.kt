package com.murray.tasklist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.tasklist.databinding.FragmentTaskListBinding

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

        setUpUserRecycler()
        binding.btnCreateTask.setOnClickListener {
            findNavController().navigate(com.murray.invoice.R.id.action_taskListFragment_to_taskCreationFragment3)
        }
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(com.murray.invoice.R.id.action_taskListFragment_to_taskDetailFragment)
    }

    private fun setUpUserRecycler() {
        val adapter = TaskAdapter(getUpDataSetUser(), requireContext(), this)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun getUpDataSetUser(): MutableList<listaTarea> {
        val dataset: MutableList<listaTarea> = ArrayList()
        dataset.add(listaTarea("Citación", "Antonio García", "Privado", "Modificado"))
        dataset.add(listaTarea("Visita fábrica", "Estela Perez", "Llamada", "Vencido"))
        dataset.add(listaTarea("Ver presupuesto", "Alejandro Castaño", "Visita", "Pendiente"))
        dataset.add(listaTarea("Cancelar visita", "Fernando Carmona", "Visita", "Modificado"))
        dataset.add(listaTarea("Agendar", "Marina Rey", "Llamada", "Vencido"))
        dataset.add(listaTarea("Citación", "Daniel Hernandez", "Visita", "Vencido"))
        dataset.add(listaTarea("Agendar", "David García", "Llamada", "Vencido"))
        dataset.add(listaTarea("Ver resultados", "Mateo Chupetón", "Privado", "Pendiente"))
        dataset.add(listaTarea("Ver informe", "Lucia Cabrera", "Privado", "Modificado"))
        dataset.add(listaTarea("Agendar", "Cristiano Ronaldo", "Privado", "Vencido"))
        dataset.add(listaTarea("Citación", "Federico Valverde", "Visita", "Pendiente"))
        return dataset
    }
}

