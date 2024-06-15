package com.example.loginapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RegistroAdapterActivity(private val registros: MutableList<Registro>) : RecyclerView.Adapter<RegistroAdapterActivity.RegistroViewHolder>() {

    inner class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.id)
        val estadoTextView: TextView = itemView.findViewById(R.id.estado)
        val tipoTextView: TextView = itemView.findViewById(R.id.tipo)
        val registroTextView: TextView = itemView.findViewById(R.id.registro)
        val fechaTextView: TextView = itemView.findViewById(R.id.fecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_registroadapter, parent, false)
        return RegistroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        val registro = registros[position]
        holder.idTextView.text = registro.idregistro.toString()
        holder.estadoTextView.text = registro.idestado_registro.toString()
        holder.tipoTextView.text = registro.idtipo_registro.toString()
        holder.registroTextView.text = registro.registro
        holder.fechaTextView.text = registro.fecha
    }

    override fun getItemCount(): Int {
        return registros.size
    }

    fun addRegistro(registro: Registro) {
        registros.add(registro)
        notifyItemInserted(registros.size - 1)
    }
}