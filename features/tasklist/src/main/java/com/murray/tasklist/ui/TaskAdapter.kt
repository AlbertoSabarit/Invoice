package com.murray.tasklist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.tasklist.R
import com.murray.tasklist.databinding.CardviewLayoutBinding

class TaskAdapter(
    private val dataset: MutableList<listaTarea>,
    private val context: Context
) :
    RecyclerView.Adapter<TaskAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    class ListViewHolder(private val binding: CardviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: listaTarea, context: Context) {
            with(binding) {
                txtTit.text = item.titulo
                txtCliente.text = item.nombre
                txtFechaCreacion.text = item.tarea
                txtFechaVenc.text = item.estado
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

