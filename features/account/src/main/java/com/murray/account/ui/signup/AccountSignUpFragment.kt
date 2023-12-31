package com.murray.account.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.account.R
import com.murray.account.databinding.FragmentAccountSignUpBinding
import com.murray.account.ui.signup.usecase.SignUpState
import com.murray.account.ui.signup.usecase.SignUpViewModel
import com.murray.entities.accounts.AccountException
import com.murray.entities.accounts.Email
import com.murray.entities.accounts.EnumTipoUsuario
import com.murray.entities.accounts.EnumVisibilidad
import com.murray.entities.accounts.UserSignUp
import com.murray.entities.tasks.Task


class AccountSignUpFragment : Fragment() {

    private var _binding: FragmentAccountSignUpBinding? = null

    private val viewModel: SignUpViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var twatcher: LogInTextWatcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSignUpBinding.inflate(inflater, container, false)


        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemList = EnumVisibilidad.values()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val profile = parent?.adapter?.getItem(position)
                Toast.makeText(requireActivity(), "Elemento pulsado $profile", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        with(binding.spProfile) {
            this.adapter = adapter
            onItemSelectedListener = listener
        }


        twatcher = LogInTextWatcher(binding.tieEmailSignUp)
        binding.tilEmailSignUp.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tiePasswordSignUp)
        binding.tilPasswordSignUp.addTextChangedListener(twatcher)

        twatcher = LogInTextWatcher(binding.tieConfirmsPasswordsSignUp)
        binding.tilConfirmsPasswordsSignUp.addTextChangedListener(twatcher)


        binding.btnCrearCuenta.setOnClickListener{
            var tipoDeUsuario : EnumTipoUsuario = EnumTipoUsuario.Usuario

            when(binding.spType.selectedItem) {
                "Usuario" -> tipoDeUsuario = EnumTipoUsuario.Usuario
                "Administrador" -> tipoDeUsuario = EnumTipoUsuario.Administrador
                "Invitado" -> tipoDeUsuario = EnumTipoUsuario.Invitado
                "Cliente" -> tipoDeUsuario = EnumTipoUsuario.Cliente
            }
            var visibilidad : EnumVisibilidad = EnumVisibilidad.Público

            when(binding.spProfile.selectedItem) {
                "Privado" -> visibilidad = EnumVisibilidad.Privado
                "Público" -> visibilidad = EnumVisibilidad.Público
                "Vacío" -> visibilidad = EnumVisibilidad.Vacío
            }
            val newUser =
                UserSignUp(
                    "Anónimo",
                    "Anonimín",
                    Email(binding.tilEmailSignUp.text.toString()) ,
                    tipoDeUsuario,
                    visibilidad
                )

            viewModel.validateCredentials(newUser)
            viewModel.state.value = SignUpState.Completed
        }

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                SignUpState.EmailEmptyError -> setEmailEmptyError()
                SignUpState.PasswordEmptyError -> setPasswordEmptyError()
                SignUpState.PasswordEmptyError2 -> setPasswordEmptyError2()
                SignUpState.PasswordsNotEquals -> setDifferentPasswordError()
                SignUpState.InvalidFormat -> setInvalidFormat()
                SignUpState.Completed -> {}
                is SignUpState.UserExist -> setUsuarioExiste()
                is SignUpState.Loading -> onLoading(it.value)
                SignUpState.Success -> onSuccess()
            }
        })
    }
    private fun setUsuarioExiste() {
        binding.tieEmailSignUp.error = "Usuario ya existe"
        Toast.makeText(context, "El usuario ya existe en el repositorio", Toast.LENGTH_SHORT).show()
        binding.tieEmailSignUp.requestFocus()
    }
    private fun onLoading(value : Boolean) {
        if(value){
            findNavController().navigate(R.id.action_accountSignUpFragment_to_fragmentProgressDialog)
        }
        else{
            findNavController().popBackStack()
        }
    }
    private fun setEmailEmptyError() {
        binding.tieEmailSignUp.error = getString(com.murray.account.R.string.errEmailEmpty)
        binding.tieEmailSignUp.requestFocus()
    }

    private fun setPasswordEmptyError() {
        binding.tiePasswordSignUp.error = getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tiePasswordSignUp.requestFocus()
    }

    private fun setPasswordEmptyError2() {
        binding.tieConfirmsPasswordsSignUp.error =
            getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tieConfirmsPasswordsSignUp.requestFocus()
    }

    private fun setDifferentPasswordError() {
        binding.tieConfirmsPasswordsSignUp.error = "Las contraseñas deben de ser iguales"
        binding.tieConfirmsPasswordsSignUp.requestFocus()
    }

    private fun setInvalidFormat(){
        binding.tieEmailSignUp.error = "Formato de email no válido"
        binding.tieEmailSignUp.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireActivity(), "Usuario registrado", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open inner class LogInTextWatcher(var tilError: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            tilError.error = null
        }

    }
}