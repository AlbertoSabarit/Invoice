package com.murray.tasklist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.murray.tasklist.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.tasklist.databinding.FragmentTaskListBinding


class TaskListFragment : Fragment() {

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
        binding.btnCreateTask.setOnClickListener{
            //findNavController().navigate()
            findNavController().navigate(com.murray.invoice.R.id.action_taskListFragment_to_taskCreationFragment3)
        }
    }

    private fun setUpUserRecycler(){
        //Crear el Adapter con los valores en el constructor primario
        var adapter: AdapterPersonalizado = AdapterPersonalizado (getUpDataSetUser(), requireContext())

        //1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }

    private fun getUpDataSetUser(): MutableList<listaTarea> {
        var dataset: MutableList<listaTarea> = ArrayList()
        dataset.add(listaTarea("Antonio García", "Privado", "Modificado"))
        dataset.add(listaTarea("Carlos Perez", "Llamada", "Vencido"))
        dataset.add(listaTarea("Alejandro Castaño", "Visita", "Pendiente"))
        dataset.add(listaTarea("Fernando Carmona", "Llamada", "Modificado"))
        dataset.add(listaTarea("Marina Del Rey", "Llamada", "Vencido"))
        dataset.add(listaTarea("Daniel Hernandez", "Visita", "Vencido"))
        dataset.add(listaTarea("David García", "Llamada", "Vencido"))
        dataset.add(listaTarea("Mateo Loco", "Privado", "Pendiente"))
        dataset.add(listaTarea("Cristobal San Francisco", "Privado", "Modificado"))
        dataset.add(listaTarea("Cristiano Ronaldo", "Privado", "Vencido"))
        dataset.add(listaTarea("Leo Messi", "Llamada", "Pendiente"))
        dataset.add(listaTarea("Federico Valverde", "Visita", "Pendiente"))
        return dataset
    }
}