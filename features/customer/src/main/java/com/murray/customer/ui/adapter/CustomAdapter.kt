package com.murray.customer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.customer.databinding.CardviewLayoutBinding
import com.murray.data.customers.Customer

class CustomAdapter (
    //private val dataset: MutableList<Customer>,
    private val context: Context,
    private val deleteClickListener: (customer: Customer) -> Unit,
    private val clickListener: (Customer) -> Unit) : RecyclerView.Adapter<CustomAdapter.ListViewHolder>() {

    private var dataset:ArrayList<Customer> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset[position]
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
    fun remove(position: Int){
        dataset.removeAt(position)
        notifyItemRemoved(position)
    }
    fun update(newDataSet: ArrayList<Customer>){
        dataset = newDataSet
        notifyDataSetChanged()
    }

    fun sort(){
        dataset.sortBy { it.name }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
