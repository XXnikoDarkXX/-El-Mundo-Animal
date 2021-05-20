package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Pregunta
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database
import java.util.Random


class Juego : AppCompatActivity() {
    val txtVida:TextView by lazy { findViewById<TextView>(R.id.txtVida) }
    val txtIdPregunta:TextView by lazy { findViewById<TextView>(R.id.txtIdPregunta) }
    val txtPregunta:TextView by lazy { findViewById(R.id.txtPregunta) }
    var contador:Int = 3
    val btnA: Button by lazy { findViewById<Button>(R.id.btnA) }
    val btnB: Button by lazy { findViewById<Button>(R.id.btnB) }
    val btnC: Button by lazy { findViewById<Button>(R.id.btnC) }
    val btnD: Button by lazy { findViewById<Button>(R.id.btnD) }
    val imgAnimal:ImageView by lazy { findViewById(R.id.imgAnimal) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)


        txtVida.text=txtVida.text.toString()+" "+contador

        //iniciamos el juego
        iniciarJuego()

        //Obtenemos las preguntas del juego
        obtenerPreguntas()



        //metemos por primera vez una pregunta aleatoria
        ponerPreguntaAleatoria()



    }



    fun iniciarJuego() {

        Database.firebaseDB.collection("juego_usuario").get().addOnSuccessListener { result ->

            for (document in result) {

                var pregunta = document.toObject(Pregunta::class.java) as Pregunta
                borrarAnimalBBDD(pregunta.id)
            }

        }
    }


    fun borrarAnimalBBDD(id:String ){
        Database.firebaseDB.collection("juego_usuario").document(id).delete().addOnCompleteListener {


        }

    }

    fun obtenerPreguntas(){

        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->


            for (document in result) {

                var pregunta = document.toObject(Pregunta::class.java) as Pregunta
                //Toast.makeText(this, ""+pregunta.id + result.size()+pregunta.descripcion, Toast.LENGTH_LONG).show()


                 Database.firebaseDB.collection("juego_usuario").document(pregunta.id).set(pregunta).addOnCompleteListener(this,
                     OnCompleteListener { task ->
                         if (task.isSuccessful) {

                         }else{
                             Toast.makeText( this,"pregunta no insertada", Toast.LENGTH_LONG).show()
                         }
                     })
            }



        }
            .addOnFailureListener { exception ->
                Toast.makeText(this,""+exception,Toast.LENGTH_LONG).show()
                //   Log.d(TAG, "Error getting documents: ", exception)
            }




    }

    fun ponerPreguntaAleatoria(){
        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->
            val preguntasTotales :ArrayList<Pregunta> = ArrayList<Pregunta>()

            for (document in result) {

                var pregunta = document.toObject(Pregunta::class.java) as Pregunta
                preguntasTotales.add(pregunta)

            }

           val indice= rand(0,preguntasTotales.size)
            //Damos valores
            txtPregunta.text=preguntasTotales.get(indice).descripcion
            txtIdPregunta.text=preguntasTotales.get(indice).id

            Glide.with(this)
                .load(preguntasTotales.get(indice).imagen)
                .into(imgAnimal)
            btnA.text=preguntasTotales.get(indice).a
            btnB.text=preguntasTotales.get(indice).b
            btnC.text=preguntasTotales.get(indice).c
            btnD.text=preguntasTotales.get(indice).d

        }
            .addOnFailureListener { exception ->
                Toast.makeText(this,""+exception,Toast.LENGTH_LONG).show()
                //   Log.d(TAG, "Error getting documents: ", exception)
            }

    }




    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }


}