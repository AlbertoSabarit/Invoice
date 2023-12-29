package com.murray.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.task.databinding.CardviewLayoutBinding
import com.murray.entities.tasks.Task

class TaskAdapter(
    private val listener: onTaskClick) :
    RecyclerView.Adapter<TaskAdapter.ListViewHolder>() {

    private var dataset = arrayListOf<Task>()

    interface onTaskClick {

        fun onClickDetails(task: Task)
        fun userOnLongClickDelete(task: Task) : Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item)

        holder.binding.root.setOnClickListener() { _ ->
            listener.onClickDetails(item)
        }

        holder.binding.root.setOnLongClickListener { _ ->
            listener.userOnLongClickDelete(item)
            true
        }
    }

    fun update(newDataSet: ArrayList<Task>) {
        dataset = newDataSet
        notifyDataSetChanged()
    }

    fun sortPersonalizado() {
        dataset.sortBy { it.titulo }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: CardviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.txtTit.text = item.titulo
            binding.txtCliente.text = item.nombre
            binding.txtTipo.text = item.tipoTarea
            binding.txtEstado.text = item.estado

        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
