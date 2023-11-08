package com.moronlu18.tasklist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.moronlu18.tasklist.R

class AdapterPersonalizado(private val elevations: List<String>) : RecyclerView.Adapter<AdapterPersonalizado.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardview: CardView = itemView.findViewById(R.id.cvcardviewTaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elevationString = elevations[position]

        val elevation = elevationString.toFloatOrNull() ?: 0.0f
        holder.cardview.cardElevation = elevation
    }

    override fun getItemCount() = elevations.size
}

