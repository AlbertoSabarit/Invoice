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
    override fun onStart() {
        super.onStart()
        viewmodel.getUserList()
    }

    private fun onSuccess(dataset: ArrayList<User>){
        hideNoDataError()
        userAdapter.update(dataset)
    }

    private fun hideNoDataError() {
        binding.animationView.visibility = View.GONE
        binding.rvUser.visibility = View.VISIBLE
    }


    private fun showNoDataError(){
        binding.animationView.visibility = View.VISIBLE
        binding.rvUser.visibility = View.GONE
    }

    private fun showProgressBar(value : Boolean){
       if(value)
            findNavController().navigate(R.id.action_userListFragment_to_fragmentProgressDialog)
        else
            findNavController().popBackStack()
    }

    private fun setUpUserRecycler(){
        userAdapter = UserAdapter ( this){
            Toast.makeText(requireContext(), "Usuario seleccionado mediante lambda $it", Toast.LENGTH_SHORT).show()
        }


        with(binding.rvUser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = userAdapter
        }
    }

    override fun userClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion cota en el usuario $user", Toast.LENGTH_SHORT).show()
    }

    override fun userOnLongClick(user: User) {
        Toast.makeText(requireActivity(), "Pulsacion larga en el usuario $user", Toast.LENGTH_SHORT).show()
    }

}