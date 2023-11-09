package com.murray.itemlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murray.itemlist.R
import com.murray.itemlist.databinding.FragmentItemListBinding


class ItemListFragment : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUserRecycler()
        binding.btnCrearArticulo.setOnClickListener{
            //findNavController().navigate()
            findNavController().navigate(com.murray.invoice.R.id.action_itemListFragment_to_itemCreationFragment)
        }
    }

    private fun setUpUserRecycler(){
        var adapter: ItemListAdapter = ItemListAdapter (getUpDataSetUser(), requireContext())

        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }

    private fun getUpDataSetUser(): MutableList<Item> {
        var dataset: MutableList<Item> = ArrayList()
        dataset.add(Item("Maleta de Cuero", "Producto", "60€", "Sí"))
        dataset.add(Item("Lápiz", "Producto", "0.5€", "No"))
        dataset.add(Item("Cuaderno", "Producto", "3€", "No"))
        dataset.add(Item("Portátil", "Producto", "700€", "Sí"))
        return dataset
    }

}