package com.murray.account.ui.sigin


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.murray.account.R
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
    private lateinit var twatcher:LogInTextWatcher
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRegsiter.setOnClickListener {
            findNavController().navigate(com.murray.account.R.id.action_accountSignInFragment_to_accountSignUpFragment)
        }

        twatcher= LogInTextWatcher(binding.tilEmail)
        binding.tieEmailSignIn.addTextChangedListener(twatcher)

        twatcher= LogInTextWatcher(binding.tilPasswordSignIn)
        binding.tiePasswordSignIn.addTextChangedListener(twatcher)

        viewModel.getState().observe(viewLifecycleOwner, Observer {//importante este metodo que recoge lo de vista/modelo(creo)
            when(it){
                SignInState.EmailEmptyError -> setEmailEmptyError()
                SignInState.PasswordEmptyError -> setPasswordEmptyError()
                is SignInState.AuthenticationError -> showMessage(it.message)
                is SignInState.Loading -> showProgressbar(it.value)
                else -> onSuccess()
            }
        })

    }

    /**
     * Mostrar un progressbar en el comienzo de una opación larga como es una consulta
     * a la base de datos, Firebase o bien ocultar cuando al operación ha terminado
     */
    private fun showProgressbar(value: Boolean) {
        if(value)
            findNavController().navigate(R.id.action_accountSignInFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    /**
     * Función que muestra al usuario un mensaje
     */
    private fun showMessage(message: String) {
        //Toast.makeText(requireContext(), "Mi primer MVVM $message", Toast.LENGTH_SHORT).show()
        val action = AccountSignInFragmentDirections.actionAccountSignInFragmentToBaseFragmentDialog("Error","Incorrecto")
        findNavController().navigate(action)
    }

    /**
     * Función que muestra el error de Email Empty
     */
    private fun setEmailEmptyError() {
        binding.tilEmail.error = getString(com.murray.account.R.string.errEmailEmpty)
        binding.tilEmail.requestFocus()
    }

    private fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tilPasswordSignIn.requestFocus()
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

