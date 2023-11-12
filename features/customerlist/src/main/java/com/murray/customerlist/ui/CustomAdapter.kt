package com.murray.customerlist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.customerlist.R
import com.murray.customerlist.databinding.CardviewLayoutBinding

class CustomAdapter ( private val dataset: MutableList<Customer>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    class ListViewHolder(val binding: CardviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Customer, context: Context) {
            binding.tvName.text = item.name
            binding.txtEmail.text = item.email
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
