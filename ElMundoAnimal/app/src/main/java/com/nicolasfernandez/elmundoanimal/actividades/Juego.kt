package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Pregunta
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database

class Juego : AppCompatActivity() {
    val txtVida:TextView by lazy { findViewById<TextView>(R.id.txtVida) }
    var contador:Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        txtVida.text=txtVida.text.toString()+" "+contador

        var preguntas:MutableList<Pregunta> = ArrayList<Pregunta>()
        var preguntastotales:MutableList<Pregunta> = ArrayList<Pregunta>()

        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->
                if (result.size()<result.size()-1) {
                    for (document in result) {
                        var pregunta = document.toObject(Pregunta::class.java) as Pregunta
                        Toast.makeText(this, "" + result.size(), Toast.LENGTH_LONG).show()

                        preguntas.add(pregunta)
                    }

                    preguntastotales = preguntas
                }
        }
            .addOnFailureListener { exception ->
                Toast.makeText(this,""+exception,Toast.LENGTH_LONG).show()
             //   Log.d(TAG, "Error getting documents: ", exception)
            }

        for (pregunta in preguntastotales){
            Toast.makeText(this,""+pregunta,Toast.LENGTH_LONG).show()
        }


    }

}