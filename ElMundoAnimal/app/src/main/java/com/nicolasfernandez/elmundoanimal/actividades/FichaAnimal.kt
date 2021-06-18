package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.APIConstantes
import com.nicolasfernandez.elmundoanimal.constantes.Database

/**
 * Actividad de la ficha de un animal, aqui mostraremos los diferentes animales que el usuario elija
 */
class FichaAnimal : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    val txtDescripcion: TextView by lazy { findViewById<TextView>(R.id.txtDescripcion) }//la descripcion del animal
    val imgFoto: ImageView by lazy { findViewById<ImageView>(R.id.imgFoto) }//Imagen del animal
    val txtTipo: TextView by lazy { findViewById<TextView>(R.id.txtTipo) }//que tipo es el animal
    val txtNombre: TextView by lazy { findViewById<TextView>(R.id.txtNombreAnimal) }//nombre del animal
    val cartaContenido: CardView by lazy { findViewById<CardView>(R.id.cardFichaAnimal) }//card donde contiene los datos de animal
    val cartaVideo: CardView by lazy { findViewById<CardView>(R.id.cardFichaAnimal2) }// card donde contiene el video del animal

    val loading: ProgressBar by lazy { findViewById<ProgressBar>(R.id.cargaFicha) }//loading
    lateinit var animal: Animal//objeto animal que usaremos para cargar el animal elegido y mostrarlo en la ficha
    var animalEncontrado:Boolean = false//boolean de control para saber si hemos encontrado el animal
    val reproductor: YouTubePlayerView by lazy { findViewById<YouTubePlayerView>(R.id.reproductor) }//reproductor de youtube
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_animal)
        var bundle: Bundle? = this.intent.extras
        var nombreAnimalPeticion: String? = bundle?.getString("ficha")
        if (nombreAnimalPeticion != null) {
            sacarFichaAnimal("peticionAnimales", nombreAnimalPeticion)
        }

        var nombreAnimalBuscado: String? = bundle?.getString("animalBuscado")
        if (nombreAnimalBuscado != null) {
            buscarAnimal(nombreAnimalBuscado)


            Handler(Looper.getMainLooper()).postDelayed({
                if (!animalEncontrado) {
                    startActivity(Intent(this, Principal::class.java))
                    Toast.makeText(this,"Animal no encontrado",Toast.LENGTH_LONG).show()
                }
            }, 3000)
        }

        var fichaSeccionAnimal: String? = bundle?.getString("fichaSeccionAnimal")
        var fichaEspecieAnimal: String? = bundle?.getString("fichaEspecieAnimal")
        if (fichaEspecieAnimal != null && fichaSeccionAnimal != null) {
            sacarFichaAnimal(fichaEspecieAnimal.toLowerCase(), fichaSeccionAnimal)

        }






    }

    /**
     * Funcion para cargar el video de yt
     * @param p0 reproductor
     * @param p1 donde cargaremos la clave del video de YT
     * @param p2 booleano para saber si ha fallado o no
     */
    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (p1 != null) {
            p1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

            p1.loadVideo(animal.video)
            p1.play()
            loading.visibility = View.GONE
            cartaContenido.visibility = View.VISIBLE

            if (!animal.video.equals("")) {
                cartaVideo.visibility = View.VISIBLE

            }
        }
    }

    /**
     * Funcion en caso de que falle la carga del video
     */
    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("Not yet implemented")
    }

    /**
     * Funcion para sacar la ficha del animal elegido de la bbdd
     * @param coleccion donde se encuentra el animal esto lo sabremos gracias al la variable tipo del animal
     * @param documento nombre del animal
     * mediante esta funcion encontraremos de la bbdd de firestore el animal y la cargaremos, completaremos la ficha con este ultimo
     * incluyendo el video llamando a las demas funciones
     *
     */
    fun sacarFichaAnimal(coleccion: String, documento: String) {

        val docRef = Database.firebaseDB.collection(coleccion).document(documento)
        docRef.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot != null) {
                val ave = documentSnapshot.toObject(Animal::class.java)
                //Toast.makeText(this,""+ documentSnapshot.data.toString(),Toast.LENGTH_LONG).show()
                if (ave != null) {
                    animal = ave
                }
                txtDescripcion.text = animal.descripcion
                txtTipo.text = "Tipo: " + animal.tipo
                Glide.with(this)
                    .load(animal.foto)
                    .into(imgFoto)
                txtNombre.text = animal.nombre.capitalize()
                reproductor.initialize(APIConstantes.APIYoutube, this)

            }
        }


    }

    /**
     * Funcion para buscar animal con el nombre de todas las colecciones
     * @param nombre del animal
     */
    fun buscarAnimal(nombre: String) {

        val array: ArrayList<String> = ArrayList()
        array.add("aves")
        array.add("insectos")
        array.add("peces")
        array.add("reptiles")
        array.add("mamifero")




            for (especie in array) {
                if (!animalEncontrado) {


                    val docRef =
                        Database.firebaseDB.collection(especie.toString()).document(nombre)
                    docRef.get().addOnSuccessListener { documentSnapshot ->

                        if (documentSnapshot != null) {
                            val ave = documentSnapshot.toObject(Animal::class.java)
                            //Toast.makeText(this,""+ documentSnapshot.data.toString(),Toast.LENGTH_LONG).show()
                            if (ave != null) {
                                animalEncontrado=true
                                animal = ave
                                txtDescripcion.text = animal.descripcion
                                txtTipo.text = "Tipo: " + animal.tipo
                                Glide.with(this)
                                    .load(animal.foto)
                                    .into(imgFoto)
                                txtNombre.text = animal.nombre
                                reproductor.initialize(APIConstantes.APIYoutube, this)


                            }



                        }
                    }

            }

        }





    }


}