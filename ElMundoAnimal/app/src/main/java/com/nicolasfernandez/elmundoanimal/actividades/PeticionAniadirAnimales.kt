package com.nicolasfernandez.elmundoanimal.actividades

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database
import java.util.regex.Pattern

/**
 * Actividad donde el usuario podrá subir los diferentes animales
 * Tendremos un formulario donde el usuario tendra que cumplimentar
 * por ultimo insertaremos el animal en la bdd de peticion del animal
 */
class PeticionAniadirAnimales : AppCompatActivity() {
    val spinnerTipos: Spinner by lazy { findViewById<Spinner>(R.id.spinnerTipos) }//Spinner con los posibles tipos de animales
    val editTextNombreAnimal: EditText by lazy { findViewById<EditText>(R.id.editTextNombreAnimal) }//EditText del nombre
    val editTextDescripcion: EditText by lazy { findViewById<EditText>(R.id.editTextDescripcion) }//EditText de la descripcion
    val editTextUrl: EditText by lazy { findViewById<EditText>(R.id.editTextUrl) }//editText de la url de la foto
    val editTextVideo: EditText by lazy { findViewById<EditText>(R.id.editTextVideo) }//editText de url del video de YT
    lateinit var tipo: String//tipo del animal
    override fun onCreate(savedInstanceState: Bundle?) {

        val context = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peticion_aniadir_animales)
        tipo = "aves"
        /**
         * Comprobamos del spinner que ha elegido el usuario
         */
        spinnerTipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        Toast.makeText(context, "aves", Toast.LENGTH_LONG).show()
                    }
                    1 -> {
                        tipo = "insectos"
                        Toast.makeText(context, "Insectos", Toast.LENGTH_LONG).show()

                    }
                    2 -> {
                        tipo = "mamifero"
                        Toast.makeText(context, "mamifero", Toast.LENGTH_LONG).show()

                    }
                    3 -> {
                        tipo = "peces"
                        Toast.makeText(context, "peces", Toast.LENGTH_LONG).show()
                    }
                    4 -> {
                        tipo = "reptiles"
                        Toast.makeText(context, "reptiles", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    /**
     * Funcion para añadir el animal a la bbbdd de peticiones de animales
     * tenemos validacion de campos vacios, con el cumplimiento del formulario creamos un objeto de tipo animal
     * insertando en la bbdd peticion de animal
     * @param view boton
     */
    fun clickAniadir(view: View) {
        if (editTextNombreAnimal.text.toString().equals("") || editTextUrl.text.toString()
                .equals("") || editTextVideo.text.toString().equals("")
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Campos vacios")
            builder.setMessage("Uno o mas campos estan vacios")
            builder.setPositiveButton("Aceptar", null)

            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {

            var nombre: String = editTextNombreAnimal.text.toString().toLowerCase()
            var fotoUrl: String = editTextUrl.text.toString()
            var descripcion = editTextDescripcion.text.toString()
            var video: String = obtenerValueVideo(editTextVideo.text.toString())
            val animal: Animal = Animal(tipo, nombre, fotoUrl, descripcion, video)
            Database.firebaseDB.collection("peticionAnimales").document(animal.nombre).set(animal)
                .addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Peticion hecha correctamente", Toast.LENGTH_LONG)
                                .show()
                            startActivity(Intent(this, Principal::class.java))

                        } else {
                            Toast.makeText(
                                this,
                                "animal no insertado en la colleccion",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

        }


    }

    /**
     * Funcion mediante regex obtendremos el value de un enlace de un video de Youtube
     * @param video url del video de youtube
     * @return un string con el value del video youtube
     */
    fun obtenerValueVideo(video: String): String {

        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(video)
        var id: String = "";
        if (matcher.find()) {
            id = matcher.group()

        }
        return id

    }
}