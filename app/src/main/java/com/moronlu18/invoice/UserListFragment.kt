package com.moronlu18.invoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.moronlu18.invoice.adapter.UserAdapter
import com.moronlu18.invoice.data.model.User
import com.moronlu18.invoice.databinding.FragmentUserListBinding

class UserListFragment : Fragment() {

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
     * Funcion que inicializa el RecyclerView que meustra el listado de usuarios de la app
     */
    private fun setUpUserRecycler(){
        //Crear el Adapter con los valores en el constructor primario
        var adapter: UserAdapter = UserAdapter (getUpDataSetUser(), requireContext())

        //1. ¿Como quiero que se muestren los elementos de la lista?
        with(binding.rvuser){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }

    private fun getUpDataSetUser(): MutableList<User> {
        var dataset: MutableList<User> = ArrayList()
        dataset.add(User("Alberto", "Sabarit", "albertosabarit@iesportada.org"))
        dataset.add(User("Ender", "Watts", "enderwatts@iesportada.org"))
        dataset.add(User("Kateryna", "Nikitenko", "katerynanikitenko@iesportada.org"))
        dataset.add(User("Ale", "Valle", "alevalle@iesportada.org"))
        return dataset
    }
}