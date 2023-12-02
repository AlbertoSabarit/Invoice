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

    //Se crea la colección de datos del adapter
    private var dataset = (arrayListOf<User>())


    /**
     * Esta interfaz es el contrato entre el adapter y el fragmento que lo contiene
     */

    interface OnUserClick {
        fun userClick(user: User) //Pulsacion corta
        fun userOnLongClick(user: User) //Pulsacion larga
        //fun deleteClick(user:User) //Eliminar usuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        holder.bind(item)
    }

    /**
     * Función que devuelve el número de elementos y por tanto se llamará al metodo onCreateViewHolder tantas veces
     * como item se visualice en el recyclerView
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     * Funcion que actualiza los datos del adapter y le dice a la vista que se invalide y vuelva a dibujarse
     */
    fun update(newDataSet: ArrayList<User>) {
        //Actualizar mi dataset y notificar a la vista el cambio

        dataset = newDataSet

        notifyDataSetChanged()
    }

    /**
     * La clase ViewHolder contiene todos los elementos de View o del layout XML que sea ha inflado
     */

    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: User) {
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