package com.murray.customer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murray.customer.databinding.CardviewLayoutBinding
import com.murray.data.customers.Customer

class CustomAdapter (
    private val deleteClickListener: (customer: Customer) -> Unit,
    private val clickListener: (Customer) -> Unit) :
    ListAdapter<Customer, CustomAdapter.ListViewHolder>(CUSTOMER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
        holder.binding.imgBtDeleteItem.setOnClickListener {
            deleteClickListener(item)
        }
    }

    inner class ListViewHolder(val binding: CardviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(customer: Customer) {
            binding.txtCustomer.text = customer.name
            binding.txtEmail.text = customer.email.getEmail()
        }
    }

    fun sort(){
        submitList(currentList.sortedBy { it.email.value })
        notifyDataSetChanged()
    }

    companion object{
        private val CUSTOMER_COMPARATOR = object: DiffUtil.ItemCallback<Customer>(){
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }

}
