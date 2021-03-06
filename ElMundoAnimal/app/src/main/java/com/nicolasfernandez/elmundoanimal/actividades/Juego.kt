package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Pregunta
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database
import java.util.Random


/**
 * /
 * Actividad Juego, en esta actividad es donde transcurrira todo el proceso de un trivia comenzando por las preguntas hasta terminar
 */
class Juego : AppCompatActivity() {
    val txtVida: TextView by lazy { findViewById<TextView>(R.id.txtVida) }//Muestra el total de vidas que tiene en partida el usuario
    val txtIdPregunta: TextView by lazy { findViewById<TextView>(R.id.txtIdPregunta) }//Muestra la id de la pregunta
    val cargaJuego :ProgressBar by lazy { findViewById(R.id.cargaJuego) }//la carga de la pregunta
    val txtPuntuacionJugador: TextView by lazy { findViewById<TextView>(R.id.txtPuntuacionJugador) }//Muestra la puntuacion el jugador

    val txtPregunta: TextView by lazy { findViewById(R.id.txtPregunta) }//texto de la pregunta
    var contador: Int = 3//contador de vidas
    val btnA: Button by lazy { findViewById<Button>(R.id.btnA) }//boton a
    val btnB: Button by lazy { findViewById<Button>(R.id.btnB) }//boton b
    val btnC: Button by lazy { findViewById<Button>(R.id.btnC) }//boton c
    val btnD: Button by lazy { findViewById<Button>(R.id.btnD) }//boton d
    val imgAnimal: ImageView by lazy { findViewById(R.id.imgAnimal) }//img de la pregunta
    var preguntasTotales: ArrayList<Pregunta> = ArrayList<Pregunta>()//array list con todas las preguntas de la bbdd esto para hacer
    //las consultas mas rapidas
    val preguntasFinalizadas: ArrayList<Pregunta> = ArrayList<Pregunta>()//arraylist con las preguntas finalizadas
    var puntuacion:Int = 0//puntuacion del usuario
    lateinit var jugador:Usuario//jugador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)
        txtPuntuacionJugador.text="Puntuacion = 0"

        txtVida.text = txtVida.text.toString() + " " + contador
      puntuacionJugador()
        //iniciamos el juego
           iniciarJuego()

        //Obtenemos las preguntas del juego
        obtenerPreguntas()


        btnA.setOnClickListener {
            clickearBoton(btnA)
        }

        btnB.setOnClickListener {
            clickearBoton(btnB)

        }

        btnC.setOnClickListener {
            clickearBoton(btnC)


        }

        btnD.setOnClickListener {
            clickearBoton(btnD)

        }


    }


    /**
     * Funcion que se ejecuta al iniciar la activid la cual nos servira para cargar  y mostrar la primera pregunta
     */
    fun iniciarJuego() {

        obtenerPreguntas()
        cargaJuego.visibility=View.GONE
        txtIdPregunta.visibility=View.VISIBLE
        imgAnimal.visibility=View.VISIBLE
        btnA.visibility=View.VISIBLE
        btnB.visibility=View.VISIBLE
        btnC.visibility=View.VISIBLE
        btnD.visibility=View.VISIBLE


    }

    /**
     * Funci??n para obtener todas las preguntas de la bbdd y almacenarlas en una lista
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

    /**
     * Funcion pora poner las preguntas aleatorias de la colecion de preguntas
     * @param preguntas: ArrayList con las preguntas de la bbdd
     */
    fun ponerPreguntaAleatoria(preguntas: ArrayList<Pregunta>) {
        if (preguntas.size > 0) {
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
        } else {
            Toast.makeText(this, "Ganastes el juego", Toast.LENGTH_LONG).show()
            terminarJuego()
        }

    }

    /**
     * Funcion para generar numero random entre dos intervalos
     */
    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (start..end).random()
    }

    /**
     * Funcion para eliminar  ya contestadas durante el transcurso del juego (las preguntas de la lista de preguntas totales)
     * @param preguntas: Coleccion con las preguntas de la bbddd
     */
    fun comprobarPreguntas(preguntas: ArrayList<Pregunta>) {

        var preguntasRestantes: ArrayList<Pregunta> = preguntas

        if (preguntasFinalizadas.size > 0) {

            for (pregunta in preguntasFinalizadas) {
                preguntasRestantes.remove(pregunta)
            }

        }

        ponerPreguntaAleatoria(preguntasRestantes)

    }

    /**
     * Funcion para que cuando clicke un boton realice las diferentes comprobaciones
     * ya sea terminar el juego porque tienes 0 de vida
     * comprobar si has acertado, restar vida, incluido generar nueva pregunta
     * @param boton boton del cual ha pulsado
     * */
    fun clickearBoton(boton: Button) {

        if (this.contador == 0) {
            terminarJuego()


        } else {


            var preguntas = preguntasTotales
            var comprobacion: Boolean = false
            for (preg in preguntas) {
                if (this.txtIdPregunta.text.toString().equals(preg.id)) {//Si la id de la pregunta actual es la misma que la encontrada
                    //en la coleccion
                    if (preg.correcto.equals(boton.text.toString())) {//y si has clickeado correctamente la pregunta

                        comprobacion = true
                        Toast.makeText(this, "Acertastes la pregunta", Toast.LENGTH_LONG).show()
                        btnA.setBackgroundResource(R.drawable.button_round)
                        btnB.setBackgroundResource(R.drawable.button_round)
                        btnC.setBackgroundResource(R.drawable.button_round)
                        btnD.setBackgroundResource(R.drawable.button_round)
                        puntuacion+=5
                        txtPuntuacionJugador.text = "Puntuacion: "+puntuacion

                    }
                }
            }

            if (comprobacion) {
                comprobarPreguntas(preguntas)

            } else {
                contador--
                txtVida.text = "Total de vidas:" + " " + contador
                Toast.makeText(this, "Fallastes ", Toast.LENGTH_LONG).show()

                //Ponemos de color rojo el boton de la pregunta
                boton.setBackgroundResource(R.drawable.button_red)

            }


        }


        if (this.contador == 0) {
            terminarJuego()

        }
    }

    /**
     * Funcion para obtener la puntuacion del jugador (a la variable jugador le cargamos el usuarios de la bbdd)
     */
    fun puntuacionJugador(){


        Database.firebaseDB.collection("usuarios").get().addOnSuccessListener { result ->

            for (jugador in result) {

                var jugador = jugador.toObject(Usuario::class.java) as Usuario
                //Toast.makeText(this, ""+pregunta.id + result.size()+pregunta.descripcion, Toast.LENGTH_LONG).show()

                if (jugador.email.equals(Database.firebaseAuth.currentUser.email)){
                    this.jugador=jugador

                }
            }

        }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "" + exception, Toast.LENGTH_LONG).show()
                //   Log.d(TAG, "Error getting documents: ", exception)
            }


    }

    /**
     * Funcion para terminar el juego y volver a la pantalla principal
     * tambien insertaremos la nueva puntuacion que ha conseguido el jugador
     */
    fun terminarJuego(){
        jugador.ranking+=puntuacion
        Database.firebaseDB.collection("usuarios").document(jugador.email.toString()).set(jugador).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this,"Has conseguido de puntuacion "+puntuacion, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText( this,"jugador no actualizado en la colleccion", Toast.LENGTH_LONG).show()
                }
            })
        startActivity(Intent(this, Principal::class.java))

    }




}