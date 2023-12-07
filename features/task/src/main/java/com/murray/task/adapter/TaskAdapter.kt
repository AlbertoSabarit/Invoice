package com.murray.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.task.databinding.CardviewLayoutBinding
import com.murray.entities.tasks.Task

class TaskAdapter(
    private val clickListener: (task: Task) -> Unit,
    private val clickDeleteListener: (task: Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ListViewHolder>() {


    private var dataset = (arrayListOf<Task>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
        holder.binding.imgbtnDelete.setOnClickListener {
            clickDeleteListener(item)
        }
    }

    fun update(newDataSet: ArrayList<Task>) {
        dataset = newDataSet
        notifyDataSetChanged()
    }

    class ListViewHolder(val binding: CardviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
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
