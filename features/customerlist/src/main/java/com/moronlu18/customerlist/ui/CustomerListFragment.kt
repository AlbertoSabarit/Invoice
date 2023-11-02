package com.moronlu18.customerlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.moronlu18.customerlist.R
import com.moronlu18.customerlist.databinding.FragmentCustomerListBinding

class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.btnCreateTask.setOnClickListener{
            //findNavController().navigate()
            //findNavController().navigate(com.moronlu18.invoice.R.id.action_taskListFragment_to_taskCreationFragment3)
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}