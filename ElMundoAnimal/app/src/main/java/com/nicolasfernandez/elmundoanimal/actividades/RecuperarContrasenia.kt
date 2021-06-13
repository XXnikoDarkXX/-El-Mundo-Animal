package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB

class RecuperarContrasenia : AppCompatActivity() {

    private val editTextResetearContrasenia: EditText by lazy { findViewById<EditText>(R.id.editTextResetearContrasenia) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenia)



    }

    fun clickEnviarReseteoContrasenia(view: View) {
        if (!editTextResetearContrasenia.text.toString().equals("")){
            var auth = Database.firebaseAuth

            auth.sendPasswordResetEmail(editTextResetearContrasenia.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Contraseña enviada al correo electronico",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Correo invalido",Toast.LENGTH_LONG).show()
                }
            }

        }else{
            Toast.makeText(this,"Campo email vacio",Toast.LENGTH_LONG).show()
        }

    }
}