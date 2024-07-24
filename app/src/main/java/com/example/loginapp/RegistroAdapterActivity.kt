package com.example.loginapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView


class RegistroAdapterActivity(private val registros: MutableList<Registro>) : RecyclerView.Adapter<RegistroAdapterActivity.RegistroViewHolder>() {

    // clase interna que representa cada elemento de la lista en el RecyclerView
    inner class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // referencias a las vistas en el layout del item de la lista
        val idTextView: TextView = itemView.findViewById(R.id.id)
        val id_cajaTextView: TextView = itemView.findViewById(R.id.id_caja)
        val sensor_1TextView: TextView = itemView.findViewById(R.id.sensor_1)
        val sensor_2TextView: TextView = itemView.findViewById(R.id.sensor_2)
        val sensor_3TextView: TextView = itemView.findViewById(R.id.sensor_3)
        val sensor_4TextView: TextView = itemView.findViewById(R.id.sensor_4)
        val sensor_5TextView: TextView = itemView.findViewById(R.id.sensor_5)
        val fechaTextView: TextView = itemView.findViewById(R.id.fecha)

        val ledVerdeImageView: ImageView = itemView.findViewById(R.id.led_verde)
        val ledAmarilloImageView: ImageView = itemView.findViewById(R.id.led_amarillo)
        val ledRojoImageView: ImageView = itemView.findViewById(R.id.led_rojo)
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
        holder.idTextView.text = "ID Registro: " + registro.id.toString()
        holder.id_cajaTextView.text = "N° Caja: " + registro.idCaja.toString()
        holder.sensor_1TextView.text = "Sensor 1: " + registro.sensor1.toString()
        holder.sensor_2TextView.text = "Sensor 2: " + registro.sensor2.toString()
        holder.sensor_3TextView.text = "Sensor 3: " + registro.sensor3.toString()
        holder.sensor_4TextView.text = "Sensor 4: " + registro.sensor4.toString()
        holder.sensor_5TextView.text = "Sensor 5: " + registro.sensor5.toString()
        holder.fechaTextView.text = "Fecha: " + registro.fechaHora

        val intensidad = registro.sensor1 + registro.sensor2 + registro.sensor3 + registro.sensor4 + registro.sensor5

        if (intensidad >= 0 && intensidad <= 499) {
            holder.ledVerdeImageView.setImageResource(R.drawable.led_verde_encendido)
            holder.ledAmarilloImageView.setImageResource(R.drawable.led_amarillo_apagado)
            holder.ledRojoImageView.setImageResource(R.drawable.led_rojo_apagado)
        } else if (intensidad >= 500 && intensidad <= 700) {
            holder.ledVerdeImageView.setImageResource(R.drawable.led_verde_apagado)
            holder.ledAmarilloImageView.setImageResource(R.drawable.led_amarillo_encendido)
            holder.ledRojoImageView.setImageResource(R.drawable.led_rojo_apagado)
        } else if (intensidad >= 701 && intensidad <= 1000) {
            holder.ledVerdeImageView.setImageResource(R.drawable.led_verde_apagado)
            holder.ledAmarilloImageView.setImageResource(R.drawable.led_amarillo_apagado)
            holder.ledRojoImageView.setImageResource(R.drawable.led_rojo_encendido)
        } else {
            // Manejo de otros casos fuera de los rangos especificados (opcional)
        }
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