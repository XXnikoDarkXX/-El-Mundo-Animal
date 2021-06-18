package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database

/**
 * Actividad carga o splash  para la aplicacion, se suele usar al principio de la app al logear
 */
class Carga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)
        var bundle: Bundle? = this.intent.extras
        var info1: String? = bundle?.getString("Principal")
        var info2: String? = bundle?.getString("Login")



        if (info1.equals("Principal")) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Principal::class.java))
            }, 3000)

        }
        if (info2.equals("Login")) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, Principal::class.java))
            }, 3000)
        }
    }



}