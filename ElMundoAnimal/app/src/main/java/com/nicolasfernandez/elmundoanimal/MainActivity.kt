package com.nicolasfernandez.elmundoanimal

import actividades.Registro
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irRegistro(view: View) {


        startActivity(Intent(this, Registro::class.java))

    }
}