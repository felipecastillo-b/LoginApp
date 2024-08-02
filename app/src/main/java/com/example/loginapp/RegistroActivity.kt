package com.example.loginapp

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import android.widget.Toast
import android.os.Handler
import java.io.IOException
import java.lang.Runnable

class RegistroActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RegistroAdapterActivity
    private val registrosList = mutableListOf<Registro>()
    private lateinit var runnable: Runnable
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // inicialización del RecyclerView y su adaptador
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RegistroAdapterActivity(registrosList)
        recyclerView.adapter = adapter

        // ejecuta tarea asíncrona para obtener registros del servidor
        GetRegistrosTask().execute()

        // configuración del Runnable para actualizar periodicamente la lista de registros
        runnable = Runnable {
            GetRegistrosTask().execute()
            handler.postDelayed(runnable, 1000) // Actualiza cada 1 segundo
        }
        handler.post(runnable) // inicia la actualizacion periodica
    }

    // clase interna para realizar la solicitud GET de registros desde el servidor de manera asincrona
    inner class GetRegistrosTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val url = URL("http://10.0.2.2/loginapp_server/getRegister.php")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            try {
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = StringBuilder()
                    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                        var line: String? = reader.readLine()
                        while (line != null) {
                            response.append(line)
                            line = reader.readLine()
                        }
                    }
                    return response.toString()
                } else {
                    // Manejar códigos de respuesta diferentes a 200 aquí si es necesario
                    throw IOException("Código de error HTTP: $responseCode")
                }
            } catch (e: IOException) {
                // Manejar excepciones de IO de red
                Log.e("RegistroActivity", "Error al leer la respuesta: ${e.message}")
                return "" // O devuelve algún valor predeterminado que indique falla
            } finally {
                connection.disconnect()
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            result?.let {
                try {
                    registrosList.clear() // limpia la lista actual de registros
                    // procesa la respuesta JSON del servidor
                    val jsonObject = JSONObject(result)
                    val registrosArray = jsonObject.getJSONArray("registros")

                     // itera sobre cada registro en el array JSON y lo agrega a la lista
                    for (i in 0 until registrosArray.length()) {
                        val registroObject = registrosArray.getJSONObject(i)
                        val registro = Registro(
                            registroObject.getInt("id"),
                            registroObject.getInt("idCaja"),
                            registroObject.getInt("sensor1"),
                            registroObject.getInt("sensor2"),
                            registroObject.getInt("sensor3"),
                            registroObject.getInt("sensor4"),
                            registroObject.getInt("sensor5"),
                            registroObject.getString("fecha")
                        )
                        registrosList.add(registro)

                        // Calcula la intensidad como la suma de los sensores
                        val intensidad = registro.sensor1 + registro.sensor2 + registro.sensor3 + registro.sensor4 + registro.sensor5

                        // Lógica para controlar LEDs según la intensidad
                        when {
                            intensidad in 0..499 -> {
                                // Encender LED verde
                                // Ejemplo: ledVerde.setImageResource(R.drawable.led_verde_encendido)
                            }
                            intensidad in 500..700 -> {
                                // Encender LED amarillo
                                // Ejemplo: ledAmarillo.setImageResource(R.drawable.led_amarillo_encendido)
                            }
                            intensidad in 701..1000 -> {
                                // Encender LED rojo
                                // Ejemplo: ledRojo.setImageResource(R.drawable.led_rojo_encendido)
                            }
                            else -> {
                                // Manejo de valores fuera de rango (opcional)
                            }
                        }
                    }

                    // actualiza el adaptador
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }

                } catch (e: Exception) {
                    // Captura cualquier excepción durante el procesamiento de la respuesta
                    Log.e(
                        "RegistroActivity",
                        "Error al procesar la respuesta: ${e.localizedMessage}"
                    )

                    // Revisa si la respuesta contiene <br> y maneja el caso específico
                    if (result.contains("<br")) {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Error en el servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Error al procesar la respuesta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                } ?: run {
                    // Result es nulo, maneja este caso si es posible
                    Toast.makeText(this@RegistroActivity, "No se pudo obtener la respuesta del servidor", Toast.LENGTH_SHORT).show()
                }
        }
    }
}