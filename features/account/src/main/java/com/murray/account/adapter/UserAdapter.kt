package com.murray.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.murray.account.databinding.LayoutUserItemBinding

class UserAdapter(
    private val dataset: MutableList<com.murray.account.data.model.User>,
    private val context: Context,
    private val listener: OnUserClick,
    private val onItemClick: (user: com.murray.account.data.model.User) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    /**
     * Esta interfaz es el contrato entre el adapter y el fragmento que lo contiene
     */

    interface OnUserClick {
        fun userClick(user: com.murray.account.data.model.User) //Pulsacion corta
        fun userOnLongClick(user: com.murray.account.data.model.User) //Pulsacion larga
        //fun deleteClick(user:User) //Eliminar usuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
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

    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //val imgUser = view.findViewById(R.id.circularImageView) as ImageView

        fun bind(item: com.murray.account.data.model.User, context: Context) {
            with(binding) {
                tvNombre.text = item.name
                tvApellido.text = item.surname
                tvEmail.text = item.email
                root.setOnClickListener { _ ->
                    //Llamaré a un método de la interfaz declarada dentro del adapter
                    //Como no utilizo el parametro de entrada de tipo View, Kotlin me recomienda usar _
                    //listener.userClick(item) en vez de usar listener, ahora usaremos onItemClick
                    onItemClick(item)

                }
                //Manejar la pulsacion larga
                root.setOnLongClickListener { _ ->
                    listener.userOnLongClick(item)
                    //Se debe indicar al framework/android que se consume el evento
                    true
                    //return@setOnLongClickListener true

                }
            }
        }
    }
}