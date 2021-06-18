package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.nicolasfernandez.elmundoanimal.R

/**
 * Actividad para enviar un correo electronico al creado de la aplicación
 */
class EnviarCorreo : AppCompatActivity() {

    val editTextAsunto: EditText by lazy { findViewById<EditText>(R.id.editTextAsunto) }//editText del asunto
    val editTextContenido: EditText by lazy { findViewById<EditText>(R.id.editTextContenido) }//editText del contenido a enviar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_correo)
    }

    /**
     * Funcion para enviar el correo electronico mediante el intent sacaremos la opcion de mostrar la aplicacion de correos
     * @param view boton
     */
    fun clickEnviarEmail(view: View) {
        if (!(editTextAsunto.text.toString().equals("")&&editTextContenido.text.toString().equals(""))){
            val it:Intent = Intent(Intent.ACTION_SEND)
            val to:String= "nicocheto1@gmail.com"
            val direccion = arrayOf(to)
            it.putExtra(Intent.EXTRA_EMAIL,direccion)
            it.putExtra(Intent.EXTRA_SUBJECT,editTextAsunto.text.toString())
            it.putExtra(Intent.EXTRA_TEXT,editTextContenido.text.toString())
            it.setType("message/rfc822")
            startActivity(Intent.createChooser(it, "Elige app de correo"));

        }

    }

}