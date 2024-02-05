package com.murray.customer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murray.customer.databinding.CardviewLayoutBinding
import com.murray.data.customers.Customer
import com.murray.data.tasks.Task

class CustomAdapter (
    private val context: Context,
    private val deleteClickListener: (customer: Customer) -> Unit,
    private val clickListener: (Customer) -> Unit
) : ListAdapter<Customer, CustomAdapter.ListViewHolder>(CUSTOMER_COMPARATOR) {

    private var dataset:ArrayList<Customer> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, context)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
        holder.binding.imgBtDeleteItem.setOnClickListener {
            deleteClickListener(item)
        }
    }

    class ListViewHolder(val binding: CardviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Customer, context: Context) {
            binding.txtCustomer.text = item.name
            binding.txtEmail.text = item.email.getEmail()
        }
    }

    fun sort(){
        val sortedCustomerList = currentList.sortedBy { it.email.value}
        submitList(sortedCustomerList)
    }

    companion object {
        private val CUSTOMER_COMPARATOR = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}