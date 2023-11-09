package com.murray.tasklist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.tasklist.R

class AdapterPersonalizado(
    private val dataset: MutableList<listaTarea>,
    private val context: Context
) :
    RecyclerView.Adapter<AdapterPersonalizado.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(layoutInflater.inflate( R.layout.cardview_layout, parent,false) )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById(R.id.txtCliente) as TextView
        val tvTask = view.findViewById(R.id.txtFechaCreacion) as TextView
        val tvState = view.findViewById(R.id.txtFechaVenc) as TextView

        fun bind(item: listaTarea, context: Context) {
            tvName.text = item.nombre
            tvTask.text = item.tarea
            tvState.text = item.estado
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

