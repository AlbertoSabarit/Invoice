package com.murray.account.ui.signin


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
import com.murray.account.ui.signin.usecase.SignInState
import com.murray.account.ui.signin.usecase.SignInViewModel


class AccountSignInFragment : Fragment() {

    private var _binding: FragmentAccountSignInBinding? = null

    private val viewModel: SignInViewModel by viewModels()
    private val binding get() = _binding!!

    private lateinit var twatcher:LogInTextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountSignInBinding.inflate(inflater, container, false)

        binding.viewmodel = this.viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRegsiter.setOnClickListener {
            findNavController().navigate(R.id.action_accountSignInFragment_to_accountSignUpFragment)
        }

        twatcher= LogInTextWatcher(binding.tilEmail)
        binding.tieEmailSignIn.addTextChangedListener(twatcher)

        twatcher= LogInTextWatcher(binding.tilPasswordSignIn)
        binding.tiePasswordSignIn.addTextChangedListener(twatcher)

        viewModel.getState().observe(viewLifecycleOwner, Observer {
            when(it){
                SignInState.EmailEmptyError -> setEmailEmptyError()
                SignInState.PasswordEmptyError -> setPasswordEmptyError()
                SignInState.Completed -> {}
                is SignInState.AuthenticationError -> showMessage(it.message)
                else -> onSuccess()
            }
        })

    }
    private fun showProgressbar(value: Boolean) {
        if(value)
            findNavController().navigate(R.id.action_accountSignInFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun showMessage(message: String) {
        val action = AccountSignInFragmentDirections.actionAccountSignInFragmentToBaseFragmentDialog("Error",message)
        findNavController().navigate(action)
    }

    private fun setEmailEmptyError() {
        binding.tilEmail.error = getString(com.murray.account.R.string.errEmailEmpty)
        binding.tilEmail.requestFocus()
    }

    private fun setPasswordEmptyError() {
        binding.tilPasswordSignIn.error = getString(com.murray.account.R.string.errPasswordEmpty)
        binding.tilPasswordSignIn.requestFocus()
    }

    private fun onSuccess() {
        viewModel.state.value = SignInState.Completed
        Toast.makeText(requireActivity(), "Te has logeado", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_accountSignInFragment_to_userListFragment)
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

