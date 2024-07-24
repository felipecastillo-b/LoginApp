package com.example.loginapp

// define una clase de datos llamada Registro
data class Registro(
    val id: Int,  // ID de la entrada en la base de datos
    val idCaja: Int,
    val sensor1: Int,
    val sensor2: Int,
    val sensor3: Int,
    val sensor4: Int,
    val sensor5: Int,
    val fechaHora: String
)