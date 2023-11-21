package com.murray.account.ui.sigin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.murray.account.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRegsiter.setOnClickListener {
            findNavController().navigate(R.id.action_accountSignInFragment_to_accountSignUpFragment)
        }

        /*Este codigo ya no es necesario ya que se implementa medainte Data Binding
        binding.btnSignIn.setOnClickListener {
            //findNavController().navigate(R.id.action_accountSignInFragment_to_userListFragment)
            viewModel.validateCredentials()
        } */

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
        binding.tilEmail.error = getString(R.string.errEmailEmpty)
        binding.tilEmail.requestFocus() //El cursor del foco se coloca en el til que tiene el error
    }

    private fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(R.string.errPasswordEmpty)
        binding.tilEmail.requestFocus()
    }

    private fun onSuccess() {
        Toast.makeText(requireActivity(), "Caso de uso", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}