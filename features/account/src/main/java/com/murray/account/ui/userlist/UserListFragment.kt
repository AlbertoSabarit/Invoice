package com.murray.account.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.account.adapter.UserAdapter
import com.murray.account.databinding.FragmentUserListBinding
import com.murray.account.ui.userlist.usecase.UserListState
import com.murray.account.ui.userlist.usecase.UserListViewModel
import com.murray.entities.accounts.User
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.murray.account.R

class UserListFragment : Fragment(), UserAdapter.OnUserClick {

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

        setUpUserRecycler()

        viewmodel.getState().observe(viewLifecycleOwner, Observer{
            when(it){
                is UserListState.Loading -> showProgressBar(it.value)
                UserListState.NoDataError -> showNoDataError()
                is UserListState.Success -> onSuccess(it.dataset)
            }
        })
    }

    /**
     * Cuando el fragment se inicia debe pedir el listado de usuarios al ViewModel - Infraestructura
     * (Firebase, Json, Room...)
     */
    override fun onStart() {
        super.onStart()
        viewmodel.getUserList()
    }

    /**
     * Funcion que contiene el listado de usuarios
     */
    private fun onSuccess(dataset: ArrayList<User>){
       //Desactivar la animacion y visualizar el recyclerview
        hideNoDataError()
        userAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.animationView.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
    }


    /**
     * Funcion que muestra el error de no hay datos
     */
    private fun showNoDataError(){
        binding.animationView.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }

    /**
     * Mostrar el progressbar en la vista
     */
    private fun showProgressBar(value : Boolean){
       if(value)
            findNavController().navigate(R.id.action_userListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    /**
     * Funcion que inicializa el RecyclerView que muestra el listado de usuarios de la app
     */
    private fun setUpUserRecycler(){
        //Crear el Adapter con los valores en el constructor primario
        userAdapter = UserAdapter ( this){
            Toast.makeText(requireContext(), "Usuario seleccionado mediante lambda $it", Toast.LENGTH_SHORT).show()
        }

        //1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.rvUser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = userAdapter
        }
    }

    /**
     * Esta funcion se llama de forma asincrona cuando el usuario pulse sobre un elemento del RecyclerView
     */

    override fun userClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion cota en el usuario $user", Toast.LENGTH_SHORT).show()
    }

    override fun userOnLongClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion larga en el usuario $user", Toast.LENGTH_SHORT).show()
    }

}