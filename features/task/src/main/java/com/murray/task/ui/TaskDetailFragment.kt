package com.murray.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.murray.data.tasks.Task
import com.murray.task.R
import com.murray.task.databinding.FragmentTaskDetailBinding


class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.task = requireArguments().getParcelable(Task.TAG)

        binding.btnEditar.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelable(Task.TAG, binding.task)

            findNavController().navigate(
                R.id.action_taskDetailFragment_to_taskCreationFragment,
                bundle
            )
        }

        parentFragmentManager.setFragmentResultListener(
            "editTaskResult",
            viewLifecycleOwner
        ) { _, bundle ->
            val updatedTask = bundle.getParcelable<Task>(Task.TAG)
            binding.task = updatedTask
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}