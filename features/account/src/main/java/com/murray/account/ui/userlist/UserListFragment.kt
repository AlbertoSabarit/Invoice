package com.murray.account.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.account.adapter.UserAdapter
import com.murray.account.databinding.FragmentUserListBinding
import com.murray.account.ui.userlist.usecase.UserListState
import com.murray.account.ui.userlist.usecase.UserListViewModel
import com.murray.data.accounts.User
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.murray.account.R
import com.murray.invoice.ui.MainActivity

class UserListFragment : Fragment(), UserAdapter.OnUserClick, MenuProvider {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: UserListViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Función que personaliza el botón flotante
        setUpFab()
        //Función que personaliza el menú de la toolbar
        setUpToolbar()

        setUpUserRecycler()

        viewmodel.getState().observe(viewLifecycleOwner, Observer {
            when (it) {
                is UserListState.Loading -> showProgressBar(it.value)
                UserListState.NoDataError -> showNoDataError()
                is UserListState.Success -> onSuccess(it.dataset)
            }
        })
    }

    /**
     * Esta funcion personaliza el comprotamiento del boton flotante de la Activity
     */
    private fun setUpFab() {

        (requireActivity() as? MainActivity)?.fab?.apply {
            visibility = View.VISIBLE
            setOnClickListener { view ->
                //Acción del listener
                Snackbar.make(view, "Soy el fragment", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * Esta funcion personaliza el comprotamiento del la toolbar de la Activity
     */
    private fun setUpToolbar() {
        (requireActivity() as? MainActivity)?.toolbar?.apply {
            visibility = View.VISIBLE
        }

        val menuhost: MenuHost = requireActivity()
        menuhost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Se añade las opciones del menu definidas en R.menu.menu_user_list al menu principal
     */
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menuInflater.inflate(R.menu.menu_user_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.action_sort ->{
                userAdapter.sortPersonalizado()
                return true
            }
            R.id.action_refresh ->{
                viewmodel.getUserList()
                return true
            }
            else-> false
        }
    }
    override fun onStart() {
        super.onStart()
        viewmodel.getUserList()
    }

    private fun onSuccess(dataset: ArrayList<User>) {
        hideNoDataError()
        userAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.animationView.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
    }


    private fun showNoDataError() {
        binding.animationView.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }

    private fun showProgressBar(value: Boolean) {
        if (value)
            findNavController().navigate(R.id.action_userListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun setUpUserRecycler() {
        userAdapter = UserAdapter(this) {
            Toast.makeText(
                requireContext(),
                "Usuario seleccionado mediante lambda $it",
                Toast.LENGTH_SHORT
            ).show()
        }


        with(binding.rvUser) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = userAdapter
        }
    }

    override fun userClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion cota en el usuario $user", Toast.LENGTH_SHORT)
            .show()
    }

    override fun userOnLongClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion larga en el usuario $user", Toast.LENGTH_SHORT)
            .show()
    }


}