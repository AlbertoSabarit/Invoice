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
    private val clickListener: OnItemClickListener
) : RecyclerView.Adapter<TaskAdapter.ListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(CardviewLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item, context)

        // Agregar clic al CardView
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }
    }

    class ListViewHolder(private val binding: CardviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: com.murray.entities.tasks.Task, context: Context) {
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
