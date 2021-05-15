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




        var preguntas:ArrayList<Pregunta> = ArrayList<Pregunta>()
        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->

            for (document in result) {
                val pregunta= document.toObject(Pregunta::class.java) as Pregunta

                preguntas.add(pregunta)
            }

        }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }


        for (pregunta in preguntas){
            Toast.makeText(this,""+pregunta,Toast.LENGTH_LONG).show()
        }


    }











}