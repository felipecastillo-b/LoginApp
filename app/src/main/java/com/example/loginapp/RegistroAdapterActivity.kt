package com.example.loginapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RegistroAdapterActivity(private val registros: MutableList<Registro>) : RecyclerView.Adapter<RegistroAdapterActivity.RegistroViewHolder>() {

    // clase interna que representa cada elemento de la lista en el RecyclerView
    inner class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // referencias a las vistas en el layout del item de la lista
        val idTextView: TextView = itemView.findViewById(R.id.id)
        val estadoTextView: TextView = itemView.findViewById(R.id.estado)
        val tipoTextView: TextView = itemView.findViewById(R.id.tipo)
        val registroTextView: TextView = itemView.findViewById(R.id.registro)
        val fechaTextView: TextView = itemView.findViewById(R.id.fecha)
    }

    // metodo invocado por RecyclerView para crear nuevos ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_registroadapter, parent, false)
        return RegistroViewHolder(itemView)
    }

    // metodo invocado por RecyclerView para asociar datos a un ViewHolder especifico
    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = registros[position]
        // asignar los datos del registro a las vistas correspondientes en el ViewHolder
        holder.idTextView.text = registro.idregistro.toString()
        holder.estadoTextView.text = registro.idestado_registro.toString()
        holder.tipoTextView.text = registro.idtipo_registro.toString()
        holder.registroTextView.text = registro.registro
        holder.fechaTextView.text = registro.fecha
    }

    // metodo que devuelve la cantidad total de elementos en la lista de registros
    override fun getItemCount(): Int {
        return registros.size
    }

    // metodo para agregar un nuevo registro a la lista y notificar al adaptador
    fun addRegistro(registro: Registro) {
        registros.add(registro)
        notifyItemInserted(registros.size - 1) // notifica que se ha insertado un nuevo elemento
    }
}