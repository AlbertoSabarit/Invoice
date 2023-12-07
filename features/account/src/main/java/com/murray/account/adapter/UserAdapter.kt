package com.murray.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.account.databinding.LayoutUserItemBinding
import com.murray.entities.accounts.User

class UserAdapter(
    private val listener: OnUserClick,
    private val onItemClick: (user: User) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var dataset = (arrayListOf<User>())


    interface OnUserClick {
        fun userClick(user: User)
        fun userOnLongClick(user: User)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun update(newDataSet: ArrayList<User>) {
        //Actualizar mi dataset y notificar a la vista el cambio

        dataset = newDataSet

        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: User) {
            with(binding) {
                tvNombre.text = item.name
                tvApellido.text = item.surname
                tvEmail.text = item.email
                root.setOnClickListener { _ -> onItemClick(item) }

                root.setOnLongClickListener { _ ->
                    listener.userOnLongClick(item)
                    true
                }
            }
        }
    }
}