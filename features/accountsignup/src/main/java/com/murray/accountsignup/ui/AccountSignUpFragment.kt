package com.murray.accountsignup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.murray.accountsignup.databinding.FragmentAccountSignUpBinding


class AccountSignUpFragment : Fragment() {

    private var _binding: FragmentAccountSignUpBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountSignUpBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //1. Crear colección de datos
        val itemList = arrayListOf("Privado", "Publico", "Vacío")
        //2. Crear Adapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        /*binding.spProfile.adapter = adapter
        binding.spProfile.setSelection(2)
        //3. Inicializar el listener que se lanza cuando el usuario modifica el valor
        binding.spProfile.onItemClickListener = null*/

        //Se usa el modismo with que dado un objeto se pueden modificar propiedades dentro del bloque
        with(binding.spProfile) {
            this.adapter = adapter
            setSelection(2)
            onItemSelectedListener = null
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}