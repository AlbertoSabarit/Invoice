package com.murray.taskcreation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.murray.taskcreation.R

class TaskCreationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_creation, container, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val spinnerTipo = view.findViewById<Spinner>(R.id.spinnerTipo)
        val spinnerEstado = view.findViewById<Spinner>(R.id.spinnerEstado)

        val nombres = arrayOf("Alberto", "Christian", "Marina")
        val tipo = arrayOf("Privado", "Llamada", "Visita")
        val estado = arrayOf("Pendiente", "Modificado", "Vencido")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipo)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapterTipo

        val adapterEstado = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estado)
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstado.adapter = adapterEstado

        return view
    }
}
