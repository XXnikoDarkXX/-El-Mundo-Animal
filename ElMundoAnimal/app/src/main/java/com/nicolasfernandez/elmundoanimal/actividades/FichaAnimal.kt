package com.nicolasfernandez.elmundoanimal.actividades

import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import org.w3c.dom.Text

class FichaAnimal : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    val txtDescripcion:TextView by lazy { findViewById<TextView>(R.id.txtDescripcion) }
    val imgFoto:ImageView by lazy { findViewById<ImageView>(R.id.imgFoto) }
    val txtTipo:TextView by lazy { findViewById<TextView>(R.id.txtTipo) }
    val txtNombre:TextView by lazy { findViewById<TextView>(R.id.txtNombreAnimal) }
    val cartaContenido:CardView by lazy { findViewById<CardView>(R.id.cardFichaAnimal) }
    val cartaVideo:CardView by lazy { findViewById<CardView>(R.id.cardFichaAnimal2) }

    val loading:ProgressBar by lazy {findViewById<ProgressBar>(R.id.cargaFicha)  }
    lateinit var animal: Animal
    val reproductor:YouTubePlayerView by lazy { findViewById<YouTubePlayerView>(R.id.reproductor) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_animal)
        var bundle:Bundle?=this.intent.extras
        var nombreAnimalPeticion:String?=bundle?.getString("ficha")
        if (nombreAnimalPeticion!=null){
            sacarFichaAnimal("peticionAnimales",nombreAnimalPeticion)
        }



    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (p1 != null) {
            p1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            p1.loadVideo(animal.video)
            p1.play()
            loading.visibility=View.GONE
            cartaContenido.visibility=View.VISIBLE
            cartaVideo.visibility=View.VISIBLE
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("Not yet implemented")
    }

    fun sacarFichaAnimal(coleccion:String,documento:String){


            val docRef = Database.firebaseDB.collection(coleccion).document(documento)
            docRef.get().addOnSuccessListener { documentSnapshot ->

                if (documentSnapshot != null) {
                    val ave = documentSnapshot.toObject(Animal::class.java)
                    //Toast.makeText(this,""+ documentSnapshot.data.toString(),Toast.LENGTH_LONG).show()
                    if (ave != null) {
                        animal=ave
                    }
                   txtDescripcion.text=animal.descripcion
                    txtTipo.text="Tipo: "+animal.tipo
                    Glide.with(this)
                       .load(animal.foto)
                       .into(imgFoto)
                    txtNombre.text=animal.nombre
                    reproductor.initialize(APIConstantes.APIYoutube,this)

                }
            }


    }
}