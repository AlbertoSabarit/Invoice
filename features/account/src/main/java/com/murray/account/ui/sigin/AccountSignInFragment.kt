package com.murray.account.ui.sigin


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.account.databinding.FragmentAccountSignInBinding


class AccountSignInFragment : Fragment() {

    private var _binding: FragmentAccountSignInBinding? = null
    //Se inicializará posteriormente
    //private lateinit var viewModel: SignInViewModel

    private val viewModel: SignInViewModel by viewModels()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountSignInBinding.inflate(inflater, container, false)

        //Pasamos a la interfaz la instancia del ViewModel para que actualice/recoja los valores
        //del email y password automáticamente y asociar el evento onClick del botón a una función
        binding.viewmodel = this.viewModel

        //IMPORTANTE: Hay que establecer el Fragment/Activity vinculado al binding para actualizar
        // los valores del Binding en base al ciclo de vida

        binding.lifecycleOwner = this

        return binding.root
    }
    private lateinit var twatcher:SignInWatcher
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRegsiter.setOnClickListener {
            findNavController().navigate(com.murray.account.R.id.action_accountSignInFragment_to_accountSignUpFragment)
        }

        twatcher= SignInWatcher(binding.tilEmail)
        binding.tieEmailSignIn.addTextChangedListener(twatcher)

        twatcher= SignInWatcher(binding.tilPasswordSignIn)
        binding.tiePasswordSignIn.addTextChangedListener(twatcher)

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                SignInState.EmailEmptyError -> setEmailEmptyError()
                SignInState.PasswordEmptyError -> setPasswordEmptyError()
                else -> onSuccess()
            }
        })

    }

    /**
     * Función que muestra el error de Email Empty
     */
    private fun setEmailEmptyError() {
        binding.tilEmail.error = getString(com.murray.account.R.string.errEmailEmpty)
        binding.tilEmail.requestFocus() //El cursor del foco se coloca en el til que tiene el error
    }

    private fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tilEmail.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireActivity(), "Caso de uso", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Creamos una clase interna para acceder a las propiedades sy funciones de la clase externa
     */
    inner class SignInWatcher(var tieError: TextInputLayout) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            tieError.isErrorEnabled = false
        }

    }
}

