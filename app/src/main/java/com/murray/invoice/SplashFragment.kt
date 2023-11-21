package com.murray.invoice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murray.invoice.R
import com.murray.invoice.databinding.FragmentSplashBinding

private const val WAIT_TIME: Long = 1000 // Espera 4 segundos (variando)

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToNextFragmentAfterDelay()
    }

    private fun navigateToNextFragmentAfterDelay() {
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        }, WAIT_TIME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
