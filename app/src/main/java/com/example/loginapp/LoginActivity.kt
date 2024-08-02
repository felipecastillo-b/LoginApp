package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.json.JSONException


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Vincula las variables a los elementos de la interfaz de usuario activity_login.xml
        val editTextEmail = findViewById<EditText>(R.id.email)
        val editTextPassword = findViewById<EditText>(R.id.password)
        val buttonLogin = findViewById<Button>(R.id.submit)

        // define una acción cuando se hace clic en el boton de inicio de sesion
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // verifica que los campos de correo electronico y contraseña no estén vacios
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // llama a la funcion de inicio de sesion con los datos proporcionados
                login(email, password)
            } else {
                // muestra un mensaje de error si los campos están vacios
                Toast.makeText(this, "Porfavor ingresa un Email y Contrasenha", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Funcion para manejar el inicio de sesion
    private fun login(email: String, password: String) {
        // URL del script PHP del servidor que maneja el inicio de sesion, tambien puede ser un servidor en la nube o local
        val url = "http://10.0.2.2/loginapp_server/login.php"
        // crea una solicitud HTTP POST utilizando la libreria Volley
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener<String> { response ->
                Log.d("LoginResponse", response) // Log the response
                try {
                    // Convierte la respuesta en un objeto JSON
                    val jsonObject = JSONObject(response)
                    // Verifica si el inicio de sesion fue exitoso
                    if (!jsonObject.getBoolean("error")) {
                        // Muestra un mensaje de éxito
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                        // Inicia la actividad RegistroActivity, en donde se mostraran los registros que se enviaran
                        val intent = Intent(this, RegistroActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Muestra un mensaje de error
                        Toast.makeText(this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Log.e("LoginActivity", "Error parsing JSON response", e)
                    Toast.makeText(this, "Response parsing error", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Maneja errores de la red
                error.printStackTrace()
                Toast.makeText(this, "Network error: " + error.message, Toast.LENGTH_SHORT).show()
            }) {
            // define los parametros que se enviaran en la solicitud POST
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = email
                params["password"] = password
                return params
            }
        }

        // crea una cola de solicitudes y agrega la solicitud a la cola, facilita la gestion de solicitudes de manera asincronica
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}
