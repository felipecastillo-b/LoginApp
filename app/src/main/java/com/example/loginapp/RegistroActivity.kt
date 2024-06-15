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

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RegistroAdapterActivity(registrosList)
        recyclerView.adapter = adapter

        GetRegistrosTask().execute()

        runnable = Runnable {
            GetRegistrosTask().execute()
            handler.postDelayed(runnable, 1000)
        }
        handler.post(runnable)
    }

    inner class GetRegistrosTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            val url = URL("http://10.0.2.2/loginapp_server/getRegister.php")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val response = StringBuilder()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            return response.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            result?.let {
                try {
                    registrosList.clear()
                    val jsonObject = JSONObject(result)
                    val registrosArray = jsonObject.getJSONArray("registros")

                    for (i in 0 until registrosArray.length()) {
                        val registroObject = registrosArray.getJSONObject(i)
                        val registro = Registro(
                            registroObject.getInt("idregistro"),
                            registroObject.getInt("idestado_registro"),
                            registroObject.getInt("idtipo_registro"),
                            registroObject.getString("registro"),
                            registroObject.getString("fecha")
                        )
                        registrosList.add(registro)
                    }

                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }

                } catch (e: Exception) {
                    Log.e("RegistroActivity", "Error al procesar la respuesta: ${e.localizedMessage}")
                    Toast.makeText(this@RegistroActivity, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}