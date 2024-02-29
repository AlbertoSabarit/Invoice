package com.murray.customer.ui.creation

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hanmajid.android.tiramisu.notificationruntimepermission.createNotificationChannel
import com.hanmajid.android.tiramisu.notificationruntimepermission.sendNotification
import com.murray.customer.R
import com.murray.customer.databinding.FragmentCustomerCreationBinding
import com.murray.customer.ui.creation.usecase.CustomerCreationState
import com.murray.customer.ui.LayoutTextWatcher
import com.murray.customer.ui.creation.usecase.CustomerCreationViewModel
import com.murray.data.accounts.Email
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task


class CustomerCreationFragment : Fragment() {

    private var _binding: FragmentCustomerCreationBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CustomerCreationViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerCreationBinding.inflate(inflater, container, false)
        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this
        addTextWatcher()
        return binding.root
    }

    private fun addTextWatcher(){
        val tieName = binding.tieName
        val tieEmail = binding.tieEmail
        val textWatcherName = LayoutTextWatcher(binding.tilName)
        val textWatcherEmail = LayoutTextWatcher(binding.tilEmail)
        tieName.addTextChangedListener(textWatcherName)
        tieEmail.addTextChangedListener(textWatcherEmail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener() {
            Toast.makeText(requireContext(), R.string.toastMessage, Toast.LENGTH_SHORT).show()
        }

        binding.button.setOnClickListener {
            var phone: Int? = null
            if(viewModel.validatePhone(binding.tiePhone.text.toString())){
                phone = binding.tiePhone.text.toString().toInt()
            }
            val cliente =
                Customer(
                    binding.tieName.text.toString(),
                    Email(binding.tieEmail.text.toString()),
                    phone,
                    binding.tieCity.text.toString(),
                    binding.tieAddress.text.toString()
                )

            viewModel.validateCustomer(cliente)

        }
        viewModel.getState().observe(viewLifecycleOwner) {
            when(it){
                CustomerCreationState.NameIsMandatory -> setNameEmptyError()
                CustomerCreationState.NonExistentContact -> setEmailEmptyError()
                CustomerCreationState.EmailFormatError -> setEmailFormatError()
                CustomerCreationState.PhoneFormatError -> setPhoneFormatError()
                else -> onSuccess()
            }
        }
    }

    private fun initNotification() {
        createNotificationChannel(requireContext())
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0,  Intent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val textContext = "Cliente creado con éxito!"
        sendNotification(requireContext(),pendingIntent,"Operación completada", textContext)
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
        initNotification()
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