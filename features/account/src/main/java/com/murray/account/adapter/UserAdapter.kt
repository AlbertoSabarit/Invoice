package com.murray.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murray.account.databinding.LayoutUserItemBinding
import com.murray.data.accounts.User

class UserAdapter(
    private val listener: OnUserClick,
    private val onItemClick: (user: User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(USER_COMPARATOR) {


    interface OnUserClick {
        fun userClick(user: User)
        fun userOnLongClick(user: User)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //Se accede a un elemento de la lista interna de ListAdapter mediante el metodo getItem(position)
        val item = getItem(position)
        holder.bind(item)
    }


    fun sortPersonalizado(){
       /*currentList.sortBy { it.email.value }
        notifyDataSetChanged()*/
        submitList(currentList.sortedBy { it.email.value })
        notifyDataSetChanged()
    }


    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                tvNombre.text = user.name
                tvApellido.text = user.surname
                tvEmail.text = user.email.value
                root.setOnClickListener { _ -> onItemClick(user) }

                root.setOnLongClickListener { _ ->
                    listener.userOnLongClick(user)
                    true
                }
            }
        }
    }

    companion object{
        private val USER_COMPARATOR = object:DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
               return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

        }
    }
}