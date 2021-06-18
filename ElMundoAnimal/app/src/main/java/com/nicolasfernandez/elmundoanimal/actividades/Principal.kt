package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.fragments.FragmentJugar
import com.nicolasfernandez.elmundoanimal.fragments.FragmentPerfil
import com.nicolasfernandez.elmundoanimal.fragments.Inicio

/**
 * Actividad Principal es el contendor que vemos despues de logearnos donde tendremos una barra inferior con las diferentes secciones
 * tambien la navegacion entre fragments de la aplicacion
 */
class Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        traerNombre()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)//Barra inferior

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val inicio = Inicio.newInstance()
        openFragment(inicio)


    }

    /**
     * Funcion para navegar entre fragments pulsando al incono de cada seccion de la barra inferior
     * mediante un when (switch)  comprobaremos que fragment debemos inflar al contenedor de la actividad Principal
     */
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationInicio -> {
                val inicio = Inicio.newInstance()
                openFragment(inicio)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationJugar -> {
                val fragmentJuego = FragmentJugar.newInstance()
                openFragment(fragmentJuego)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationPerfil -> {
                val fragmentPerfil= FragmentPerfil.newInstance()
                openFragment(fragmentPerfil)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Funcion para abrir fragmentos desde la barra inferior
     * @param fragment fragment pasado por parametros que queremos mostrar
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    /**
     * Funcion para recoger el nombre de usuario labdd
     * mostraremos un toast dando la bienvenida al usuario
     */
    fun traerNombre(){

        val docRef = Database.firebaseDB.collection("usuarios").document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {
                Toast.makeText(this,"Bienvenido "+user.nombre,Toast.LENGTH_LONG).show()
            }
        }

    }

}