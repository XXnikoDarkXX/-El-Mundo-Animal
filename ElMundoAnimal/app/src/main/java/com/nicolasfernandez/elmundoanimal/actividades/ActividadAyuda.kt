package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.nicolasfernandez.elmundoanimal.R

class ActividadAyuda : AppCompatActivity() {
    val txtQueEs: TextView by lazy { findViewById<TextView>(R.id.txtQueEsjUEGO) }
    val txtDescripcion2: TextView by lazy { findViewById<TextView>(R.id.txtQueEsjUEGO) }
    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_ayuda)




    }
}