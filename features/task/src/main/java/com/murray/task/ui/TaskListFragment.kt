package com.murray.task.ui

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hanmajid.android.tiramisu.notificationruntimepermission.createNotificationChannel
import com.hanmajid.android.tiramisu.notificationruntimepermission.sendNotification
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


        var appBarConfiguration =
            AppBarConfiguration.Builder(R.id.taskListFragment)
                .setOpenableLayout((requireActivity() as MainActivity).drawer)
                .build()

        NavigationUI.setupWithNavController(
            (requireActivity() as MainActivity).toolbar,
            findNavController(),
            appBarConfiguration
        )

        binding.btnCreateTask.setOnClickListener {
            val bundle = Bundle()
            findNavController().navigate(R.id.action_taskListFragment_to_taskCreationFragment, bundle)
        }

        setUpToolbar()
        setUpTaskRecycler()

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                TaskListState.NoDataError -> showNoDataError()
                is TaskListState.Loading -> onLoading(it.value)
                TaskListState.Success -> onSuccess()
            }
        })

        viewmodel.allTask.observe(viewLifecycleOwner, Observer { tasks ->
            if (tasks.isNotEmpty()) {
                hideNoDataError()
                taskAdapter.submitList(tasks)
            } else {
                showNoDataError()
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

            R.id.action_sortTask -> {
                Snackbar.make(requireView(), "Tarea ordenada por titulo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                taskAdapter.sortPersonalizado()
                return true
            }

            R.id.action_refreshTask -> {
                Snackbar.make(requireView(), "Tarea ordenada por cliente", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                taskAdapter.submitList(viewmodel.allTask.value)
                return true
            }
            else -> false
        }
    }
    override fun onStart() {
        super.onStart()
        loadList()
    }

    private fun loadList(){
        val preferences = activity?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val orderValue = preferences!!.getString("tasks", "0")

        when (orderValue) {
            "0" -> {
                taskAdapter.sortPersonalizado()
            }
            "1" -> {
                //taskAdapter.submitList(viewmodel.allTask.value)
                viewmodel.getTaskList()
            }
        }
    }


    private fun onSuccess() {
        hideNoDataError()

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
                viewmodel.delete(task)
                viewmodel.getTaskList()

                initNotification("Tarea borrada con éxito")
            }
        }
        return true
    }

    private fun initNotification(title : String) {
        createNotificationChannel(requireContext())
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0,  Intent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val textContext = "Acción realizada con éxito"
        sendNotification(requireContext(),pendingIntent,title, textContext)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

