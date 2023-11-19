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


class ItemListFragment : Fragment(), ItemListAdapter.OnItemClickListener {

    private var _binding: FragmentItemListBinding? = null
    private val binding
        get() = _binding!!

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
            findNavController().navigate(com.murray.invoice.R.id.action_itemListFragment_to_itemCreationFragment)
            //TODO: Que se actualice ItemDetailFragment con los datos de cada Item
            //val action = ItemListFragmentDirections
        }
    }

    private fun setUpUserRecycler(){
        var adapter: ItemListAdapter = ItemListAdapter (getUpDataSetItem(), requireContext(), this)

        with(binding.recyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter=adapter
        }
    }

    private fun getUpDataSetItem(): MutableList<Item> {
        var dataset: MutableList<Item> = ArrayList()
        dataset.add(Item("Maleta de Cuero", "Producto", "60€", "Sí",R.drawable.img_maleta_cuero))
        dataset.add(Item("Lápices Acuarela", "Producto", "75€", "No",R.drawable.img_lapices_acuarela))
        dataset.add(Item("Cuaderno", "Producto", "20€", "No",R.drawable.img_cuaderno))
        dataset.add(Item("Portátil", "Producto", "700€", "Sí",R.drawable.img_portatil))
        dataset.add(Item("Pinturas al óleo", "Producto", "9€", "No",R.drawable.img_oleo))
        dataset.add(Item("Botas de nieve", "Producto", "15€", "Sí",R.drawable.img_botas_nieve))
        return dataset
    }

    override fun onItemClick(position: Int) {
        findNavController().navigate(com.murray.invoice.R.id.action_itemListFragment_to_itemDetailFragment)
    }

}