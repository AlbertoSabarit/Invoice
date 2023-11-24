package com.murray.task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.entities.tasks.Task
import com.murray.task.databinding.CardviewLayoutBinding

class TaskAdapter(
    private val dataset: MutableList<Task>,
    private val context: Context,
    private val clickListener: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ListViewHolder>() {


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

        fun bind(item: Task, context: Context) {
            binding.txtTit.text = item.titulo
            binding.txtCliente.text = item.nombre
            binding.txtTipo.text = item.tarea
            binding.txtEstado.text = item.estado
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
