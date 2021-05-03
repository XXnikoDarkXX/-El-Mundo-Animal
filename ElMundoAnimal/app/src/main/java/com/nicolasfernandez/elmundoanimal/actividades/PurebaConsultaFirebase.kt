package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database


class PurebaConsultaFirebase : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener {
    val video: String = "-y22EBFksV8"
    val keyYoutube: String = "AIzaSyBuj-rdXbj_3mXcGSOq8BeENbpvgC2vgnw"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pureba_consulta_firebase)

        val reproductorYT: YouTubePlayerView = findViewById<YouTubePlayerView>(R.id.reproductor)
        reproductorYT.initialize(keyYoutube, this)

        val imagenPrueba: ImageView = findViewById<ImageView>(R.id.imagenPrueba)
        Glide.with(this)
            .load("https://d500.epimg.net/cincodias/imagenes/2018/11/13/lifestyle/1542113135_776401_1542116070_noticia_normal.jpg")
            .into(imagenPrueba)
    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {

        if (p1 != null) {
            p1.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            p1.loadVideo(video)
            p1.play()
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if (p1 != null) {
            if (p1.isUserRecoverableError) {
                Log.d("Vaya", "" + p1.getErrorDialog(this, 1))
            }
        }
    }

    fun insertar(view: View) {
        var ave:Animal=Animal("Aves","Pelicano","http://www.estudiantes.info/ciencias_naturales/images/pelicano-sentado.png","Existen más de media docena de especies de pelícanos conocidos, pero todos tienen la famosa bolsa bajo el pico que caracteriza a estas aves.  La mandíbula superior termina en un fuerte gancho que se curva hacia abajo sobre la punta de la mandíbula inferior. Debajo de ésta hay una bolsa de piel desnuda. La lengua es corta y casi rudimentaria. Tienen la cara y la garganta desnudas, las patas cortas y la cola redondeada.  Los pelícanos están muy distribuidos en la mayor parte de las regiones cálidas y frecuentan las costas, las orillas de los lagos y los ríos. Se alimentan sobre todo de peces.  Cada especie usa una técnica diferente para cazar. Algunos pelícanos cazan nadando en grupos. Una de sus técnicas es agruparse en forma de U e ir arrastrando a los peces hacia el centro o hacia una zona de aguas bajas.  Para esto, una vez en el agua, baten sus alas contra ésta para asustar a los peces. Una vez los tienen arrinconados, solo tienen que cogerlos. ","y22EBFksV8")
        Database.firebaseDB.collection("aves").document(ave.nombre).set(ave).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this,"Insertado/Actualizado correctamente", Toast.LENGTH_LONG).show()


                }else{
                    Toast.makeText( this,"usuario no insertado/actualizado en la colleccion", Toast.LENGTH_LONG).show()
                }
            })


    }


}

