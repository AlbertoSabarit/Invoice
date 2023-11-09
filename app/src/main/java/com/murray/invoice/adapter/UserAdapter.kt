package com.murray.invoice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.murray.invoice.R
import com.murray.invoice.data.model.User

class UserAdapter (private val dataset : MutableList<User>, private val context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.layout_user_item, parent, false))
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item, context)
    }

    /**
     * Función que devuelve el número de elementos y por tanto se llamará al metodo onCreateViewHolder tantas veces
     * como item se visualice en el recyclerView
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * La clase ViewHolder contiene todos los elementos de View o del layout XML que sea ha inflado
     */

    class UserViewHolder(view : View): RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById(R.id.tvNombre) as TextView
        val tvSurname = view.findViewById(R.id.tvApellido) as TextView
        val tvEmail = view.findViewById(R.id.tvEmail) as TextView
        val imgUser = view.findViewById(R.id.circularImageView) as ImageView

        fun bind(item: User, context: Context){
            tvName.text = item.name
            tvSurname.text = item.surname
            tvEmail.text = item.email
        }
    }
}