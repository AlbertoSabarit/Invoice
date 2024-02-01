package com.murray.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murray.data.accounts.User
import com.murray.task.databinding.CardviewLayoutBinding
import com.murray.data.tasks.Task

class TaskAdapter(
    private val listener: onTaskClick
) : ListAdapter<Task, TaskAdapter.ListViewHolder>(TASK_COMPARATOR) {


    interface onTaskClick {

        fun onClickDetails(task: Task)
        fun userOnLongClickDelete(task: Task): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.root.setOnClickListener() { _ ->
            listener.onClickDetails(item)
        }

        holder.binding.root.setOnLongClickListener { _ ->
            listener.userOnLongClickDelete(item)
            true
        }
    }


    fun sortPersonalizado() {
        val sortedTaskList = currentList.sortedBy { it.cliente.name}
        submitList(sortedTaskList)
    }

    inner class ListViewHolder(val binding: CardviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.txtTit.text = item.titulo
            binding.txtCliente.text = item.cliente.name
            binding.txtTipo.text = item.tipoTarea
            binding.txtEstado.text = item.estado

        }
    }
    companion object {
        private val TASK_COMPARATOR = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.titulo == newItem.titulo
            }

        }
    }
}
