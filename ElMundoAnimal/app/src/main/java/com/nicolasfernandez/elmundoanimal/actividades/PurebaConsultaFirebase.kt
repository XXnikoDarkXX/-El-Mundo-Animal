package com.nicolasfernandez.elmundoanimal.actividades


import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database
import java.util.regex.Pattern

class PurebaConsultaFirebase : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val imagenPrueba:ImageView by lazy { findViewById(R.id.imagenPrueba) }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pureba_consulta_firebase)
        Glide.with(this)
            .load("https://d500.epimg.net/cincodias/imagenes/2018/11/13/lifestyle/1542113135_776401_1542116070_noticia_normal.jpg")
            .into(imagenPrueba)

        Toast.makeText(this,""+ Database.firebaseAuth.currentUser.email,Toast.LENGTH_LONG).show()

    }












}

