package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Pregunta
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database
import java.util.Random


class Juego : AppCompatActivity() {
    val txtVida: TextView by lazy { findViewById<TextView>(R.id.txtVida) }
    val txtIdPregunta: TextView by lazy { findViewById<TextView>(R.id.txtIdPregunta) }
    val txtPregunta: TextView by lazy { findViewById(R.id.txtPregunta) }
    var contador: Int = 3
    val btnA: Button by lazy { findViewById<Button>(R.id.btnA) }
    val btnB: Button by lazy { findViewById<Button>(R.id.btnB) }
    val btnC: Button by lazy { findViewById<Button>(R.id.btnC) }
    val btnD: Button by lazy { findViewById<Button>(R.id.btnD) }
    val imgAnimal: ImageView by lazy { findViewById(R.id.imgAnimal) }
    var preguntasTotales: ArrayList<Pregunta> = ArrayList<Pregunta>()
    val preguntasFinalizadas: ArrayList<Pregunta> = ArrayList<Pregunta>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)


        txtVida.text = txtVida.text.toString() + " " + contador

        //iniciamos el juego
        //    iniciarJuego()

        //Obtenemos las preguntas del juego
        obtenerPreguntas()


        btnA.setOnClickListener {

            var preguntas = preguntasTotales
            Toast.makeText(this, "" + preguntasTotales.size, Toast.LENGTH_LONG).show()
            var comprobacion:Boolean=false
            for (preg in preguntas) {
                if (this.txtIdPregunta.text.toString().equals(preg.id)) {
                    if (preg.correcto.equals(btnA.text.toString())) {
                        comprobacion = true
                    }
                }
            }

            if (comprobacion){
                comprobarPreguntas(preguntas)

            }else{
                contador--
                txtVida.text ="Total de vidas:" + " " + contador
                Toast.makeText(this,"Fallastes ",Toast.LENGTH_LONG).show()
            }
        }

        btnB.setOnClickListener {
            var preguntas = preguntasTotales
            Toast.makeText(this, "" + preguntasTotales.size, Toast.LENGTH_LONG).show()
            var comprobacion:Boolean=false
            for (preg in preguntas) {
                if (this.txtIdPregunta.text.toString().equals(preg.id)) {
                    if (preg.correcto.equals(btnB.text.toString())) {
                        comprobacion = true
                    }
                }
            }

            if (comprobacion){
                comprobarPreguntas(preguntas)

            }else{
                contador--
                txtVida.text ="Total de vidas:"+ " " + contador
                Toast.makeText(this,"Fallastes ",Toast.LENGTH_LONG).show()
            }
        }

        btnC.setOnClickListener {
            var preguntas = preguntasTotales
            Toast.makeText(this, "" + preguntasTotales.size, Toast.LENGTH_LONG).show()
            var comprobacion:Boolean=false
            for (preg in preguntas) {
                if (this.txtIdPregunta.text.toString().equals(preg.id)) {
                    if (preg.correcto.equals(btnC.text.toString())) {
                        comprobacion = true
                    }
                }
            }

            if (comprobacion){
                comprobarPreguntas(preguntas)

            }else{
                contador--
                txtVida.text ="Total de vidas:"+ " " + contador
                Toast.makeText(this,"Fallastes ",Toast.LENGTH_LONG).show()
            }
        }

        btnD.setOnClickListener {
            var preguntas = preguntasTotales
            Toast.makeText(this, "" + preguntasTotales.size, Toast.LENGTH_LONG).show()
            var comprobacion:Boolean=false
            for (preg in preguntas) {
                if (this.txtIdPregunta.text.toString().equals(preg.id)) {
                    if (preg.correcto.equals(btnD.text.toString())) {
                        comprobacion = true
                    }
                }
            }

            if (comprobacion){
                comprobarPreguntas(preguntas)

            }else{
                contador--
                txtVida.text ="Total de vidas:"+ " " + contador
                Toast.makeText(this,"Fallastes ",Toast.LENGTH_LONG).show()
            }
        }


    }

    /*

    fun iniciarJuego() {

        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->

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
*/
    fun obtenerPreguntas() {

        Database.firebaseDB.collection("juego").get().addOnSuccessListener { result ->
            val arrrayPreguntas: ArrayList<Pregunta> = ArrayList<Pregunta>()
            for (document in result) {

                var pregunta = document.toObject(Pregunta::class.java) as Pregunta
                //Toast.makeText(this, ""+pregunta.id + result.size()+pregunta.descripcion, Toast.LENGTH_LONG).show()


                arrrayPreguntas.add(pregunta)
            }
            this.preguntasTotales = arrrayPreguntas
            comprobarPreguntas(arrrayPreguntas)
        }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "" + exception, Toast.LENGTH_LONG).show()
                //   Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    fun ponerPreguntaAleatoria(preguntas: ArrayList<Pregunta>) {
        if (preguntas.size>0) {
            var indice = rand(0, preguntas.size - 1)

            //Damos valores
            this.preguntasFinalizadas.add(preguntas.get(indice))

            txtPregunta.text = preguntas.get(indice).descripcion
            txtIdPregunta.text = preguntas.get(indice).id

            Glide.with(this)
                .load(preguntas.get(indice).imagen)
                .into(imgAnimal)
            btnA.text = preguntas.get(indice).a
            btnB.text = preguntas.get(indice).b
            btnC.text = preguntas.get(indice).c
            btnD.text = preguntas.get(indice).d
        }else{
            Toast.makeText(this,"Ganastes el juego",Toast.LENGTH_LONG).show()
        }

    }


    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }

    fun comprobarPreguntas(preguntas: ArrayList<Pregunta>) {

        var preguntasRestantes: ArrayList<Pregunta> = preguntas

        if (preguntasFinalizadas.size > 0) {

            for (pregunta in preguntasFinalizadas) {
                preguntasRestantes.remove(pregunta)
            }

/*
            for (i in 0..this.preguntasFinalizadas.size) {
                for (pregunta in preguntas) {
                    if (preguntasFinalizadas.get(i).id.equals(pregunta.id)) {

                        preguntasRestantes.remove(pregunta)

                    }
                }

            }

            */
        }

        ponerPreguntaAleatoria(preguntasRestantes)
    }


}