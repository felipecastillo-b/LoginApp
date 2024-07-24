# Proyecto LoginApp Android

Este proyecto Android utiliza Kotlin para implementar una aplicación de login que interactúa con un servidor backend PHP para autenticar usuarios y mostrar registros. A continuación, se detalla cómo configurar y comprender los componentes principales del proyecto.

## Configuración

### Requisitos previos

- Android Studio instalado
- Dispositivo Android o Emulador para ejecutar la aplicación
- XAMPP con PHPMyAdmin instalado para configurar el servidor backend PHP y la base de datos MySQL.

### Configuración del servidor backend (PHP y MySQL)

1. **Configuración de XAMPP:**
   - Asegúrate de tener XAMPP instalado y ejecutándose.
   - Abre PHPMyAdmin y crea una base de datos llamada `loginapp`.
   - Ejecuta el script SQL proporcionado en `loginapp_server.sql` para crear las tablas necesarias (`estado`, `tipo`, `usuario` y `registro`).

2. **Configuración de archivos PHP:**
   - Coloca la carpeta `loginapp_server` dentro de `XAMPP/htdocs`.
   - Asegúrate de que los archivos PHP (`login.php`, `getRegister.php`) estén correctamente configurados y puedan acceder a la base de datos MySQL.

### Configuración del proyecto Android

1. **Clonar o descargar el proyecto:**
   - Clona este repositorio en tu máquina local usando Git:
     ```
     git clone <url-del-repositorio>
     ```
   - O descarga el proyecto como archivo ZIP y descomprímelo.

2. **Abrir en Android Studio:**
   - Abre Android Studio y selecciona `File -> Open` para abrir el proyecto.
   - Espera a que Android Studio sincronice y configure el proyecto.

3. **Configuración del código:**
   - Asegúrate de tener correctamente configurados los permisos de Internet (`android.permission.INTERNET`) en el archivo `AndroidManifest.xml`.
   - Revisa y actualiza las direcciones URL en el código Kotlin donde se hacen solicitudes HTTP al servidor PHP (`http://10.0.2.2/loginapp_server/`), puede ser tambien un servidor en la Nube.

## Estructura del proyecto

El proyecto consta de los siguientes archivos principales en Kotlin:

1. **LoginActivity.kt:**
   - Actividad principal que gestiona la autenticación de usuarios utilizando Volley para realizar solicitudes HTTP al servidor PHP.

2. **RegistroActivity.kt:**
   - Actividad que muestra una lista de registros obtenidos del servidor utilizando RecyclerView y un adaptador personalizado `RegistroAdapterActivity`.

3. **RegistroAdapterActivity.kt:**
   - Adaptador para RecyclerView que gestiona cómo se muestran los registros (`Registro`) en la lista.

4. **Registro.kt:**
   - Clase de datos (`data class`) que define la estructura de un registro con sus atributos (`idregistro`, `idestado_registro`, `idtipo_registro`, `registro`, `fecha`).

5. **GetRegistrosTask.kt:**
   - Clase AsyncTask que maneja la obtención de registros desde el servidor utilizando HttpURLConnection y procesa la respuesta JSON.

## Ejecución y prueba

- Conecta tu dispositivo Android o utiliza un emulador en Android Studio.
- Asegúrate de que el servidor XAMPP esté en funcionamiento y los archivos PHP estén correctamente configurados.
- Ejecuta la aplicación desde Android Studio y prueba las funcionalidades de login y visualización de registros.

## Notas adicionales

- Este proyecto es un ejemplo básico de cómo implementar una aplicación Android que interactúa con un servidor backend PHP para autenticar usuarios y obtener datos.
- Se recomienda mejorar la seguridad y la experiencia del usuario añadiendo validaciones adicionales y manejando errores de manera más robusta tanto en el lado del cliente como del servidor.
