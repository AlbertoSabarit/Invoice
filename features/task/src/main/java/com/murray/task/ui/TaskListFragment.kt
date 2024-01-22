package com.murray.task.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.murray.data.tasks.Task
import com.murray.invoice.ui.MainActivity
import com.murray.invoice.base.BaseFragmentDialog
import com.murray.task.R
import com.murray.task.adapter.TaskAdapter
import com.murray.task.databinding.FragmentTaskListBinding
import com.murray.task.ui.usecase.TaskListState
import com.murray.task.ui.usecase.TaskListViewModel

class TaskListFragment : Fragment(), TaskAdapter.onTaskClick, MenuProvider {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: TaskListViewModel by viewModels()

    private lateinit var taskAdapter: TaskAdapter

    val TAG = "ListTask"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        setUpTaskRecycler()

        binding.btnCreateTask.setOnClickListener {
            var bundle = Bundle()
            findNavController().navigate(
                R.id.action_taskListFragment_to_taskCreationFragment,
                bundle
            )
        }

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                TaskListState.NoDataError -> showNoDataError()
                is TaskListState.Loading -> onLoading(it.value)
                is TaskListState.Success -> onSuccess(it.dataset)
            }
        })
    }

    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_task_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.action_refreshTask -> {
                viewmodel.getTaskListOrderByCustomer()
                Snackbar.make(requireView(), "Tarea ordenada por nombre", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                return true
            }

            R.id.action_sortTask -> {
                taskAdapter.sortPersonalizado()
                Snackbar.make(requireView(), "Tarea ordenada por cliente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                return true
            }

            else -> false
        }
    }

    override fun onStart() {
        super.onStart()

        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val orderValue = preferences!!.getString("tasks", "0")


        when (orderValue) {

            "0" -> {
                viewmodel.getTaskListOrderByTitle()
                Snackbar.make(requireView(), "Tarea ordenada por título", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            "1" -> {
                viewmodel.getTaskListOrderByCustomer()
                Snackbar.make(requireView(), "Tarea ordenada por cliente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }
    }

    private fun onSuccess(dataset: ArrayList<Task>) {
        hideNoDataError()
        taskAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.animationView.visibility = View.GONE
        binding.tv1.visibility = View.GONE
        binding.tv2.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showNoDataError() {
        binding.animationView.visibility = View.VISIBLE
        binding.tv1.visibility = View.VISIBLE
        binding.tv2.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun onLoading(value: Boolean) {
        if (value)
            findNavController().navigate(R.id.action_taskListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun setUpTaskRecycler() {
        taskAdapter = TaskAdapter(this)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = taskAdapter
        }
    }

    override fun onClickDetails(task: Task) {
        var bundle = Bundle()
        bundle.putParcelable(Task.TAG, task)

        findNavController().navigate(R.id.action_taskListFragment_to_taskDetailFragment, bundle)
    }

    override fun userOnLongClickDelete(task: Task): Boolean {

        val dialog = BaseFragmentDialog.newInstance(
            "Atención",
            "¿Deseas borrarlo?"
        )

        dialog.show((context as AppCompatActivity).supportFragmentManager, TAG)

        dialog.parentFragmentManager.setFragmentResultListener(
            BaseFragmentDialog.request, viewLifecycleOwner
        ) { _, bundle ->
            val result = bundle.getBoolean(BaseFragmentDialog.result)
            if (result) {
                viewmodel.removeFromList(task)
                viewmodel.getTaskListOrderByCustomer()

                Toast.makeText(
                    requireActivity(),
                    "Tarea ${task.titulo} borrada con éxito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }


}

