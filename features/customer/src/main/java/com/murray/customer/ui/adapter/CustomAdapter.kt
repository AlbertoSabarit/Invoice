package com.murray.customer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.customer.databinding.CardviewLayoutBinding
import com.murray.entities.customers.Customer

class CustomAdapter (
    private val dataset: MutableList<Customer>,
    private val context: Context,
    private val clickListener: (Customer) -> Unit) : RecyclerView.Adapter<CustomAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    class ListViewHolder(val binding: CardviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Customer, context: Context) {
            binding.txtCustomer.text = item.name
            binding.txtEmail.text = item.email
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
