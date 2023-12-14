package com.murray.customer.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.murray.customer.databinding.FragmentCustomerEditBinding
import com.murray.customer.ui.LayoutTextWatcher
import com.murray.customer.ui.edit.usecase.CustomerEditState
import com.murray.customer.ui.edit.usecase.CustomerEditViewModel
import com.murray.repositories.CustomerRepository

class CustomerEditFragment : Fragment() {

    private var _binding: FragmentCustomerEditBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CustomerEditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerEditBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        addTextWatcher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt("id")
        viewModel.id = id
        val c = CustomerRepository.dataSet[id]
        viewModel.name.value = c.name
        viewModel.email.value = c.email.value
        viewModel.phone.value = c.phone.toString()?: null
        viewModel.city.value = c.city
        viewModel.address.value = c.address

        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                CustomerEditState.NameIsMandatory -> setNameEmptyError()
                CustomerEditState.NonExistentContact -> setEmailEmptyError()
                CustomerEditState.EmailFormatError -> setEmailFormatError()
                CustomerEditState.PhoneFormatError -> setPhoneFormatError()
                else -> onSuccess()
            }
        }
    }

    private fun addTextWatcher(){
        val tieName = binding.tieName
        val tieEmail = binding.tieEmail
        val textWatcherName = LayoutTextWatcher(binding.tilName)
        val textWatcherEmail = LayoutTextWatcher(binding.tilEmail)
        tieName.addTextChangedListener(textWatcherName)
        tieEmail.addTextChangedListener(textWatcherEmail)
    }

    fun actualizarCliente(){

    }
    private fun setPhoneFormatError() {
        binding.tilPhone.error = "El teléfono no es válido"
        binding.tilPhone.requestFocus()
    }

    private fun setEmailFormatError() {
        binding.tilEmail.error = "El email no es válido"
        binding.tilEmail.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireActivity(), "Cliente actualizado con éxito!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
        findNavController().popBackStack()
    }

    private fun setNameEmptyError() {
        binding.tilName.error = "El nombre no puede estar vacío"
        binding.tilName.requestFocus()
    }

    private fun setEmailEmptyError() {
        binding.tilEmail.error = "El email no puede estar vacío"
        binding.tilEmail.requestFocus()
    }

}