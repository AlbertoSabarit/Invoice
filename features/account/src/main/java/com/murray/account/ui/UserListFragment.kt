package com.murray.account.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.account.adapter.UserAdapter
import com.murray.repositories.UserRepository
import com.murray.account.databinding.FragmentUserListBinding
import com.murray.entities.accounts.User

class UserListFragment : Fragment(), UserAdapter.OnUserClick {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!


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
    }

    /**
     * Funcion que inicializa el RecyclerView que muestra el listado de usuarios de la app
     */
    private fun setUpUserRecycler(){
        //Crear el Adapter con los valores en el constructor primario
        var adapter = UserAdapter (UserRepository.dataSet, requireContext(), this){
            Toast.makeText(requireContext(), "Usuario seleccionado mediante lambda $it", Toast.LENGTH_SHORT).show()
        }

        //1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.rvUser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
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