package com.moronlu18.accountsignin.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.moronlu18.accountsignin.R
import com.moronlu18.accountsignin.databinding.FragmentAccountSignInBinding


class AccountSignInFragment : Fragment() {

    private var _binding: FragmentAccountSignInBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountSignInBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtRegsiter.setOnClickListener{
            //findNavController().navigate()
            findNavController().navigate(com.moronlu18.invoice.R.id.action_accountSignInFragment_to_accountSignUpFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}