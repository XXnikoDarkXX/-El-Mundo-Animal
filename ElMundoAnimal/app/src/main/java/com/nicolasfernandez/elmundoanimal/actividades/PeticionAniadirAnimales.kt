package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database

class PeticionAniadirAnimales : AppCompatActivity() {
    val spinnerTipos: Spinner by lazy { findViewById<Spinner>(R.id.spinnerTipos) }
    val editTextNombreAnimal: EditText by lazy { findViewById<EditText>(R.id.editTextNombreAnimal) }
    val editTextDescripcion: EditText by lazy { findViewById<EditText>(R.id.editTextDescripcion) }
    val editTextUrl: EditText by lazy { findViewById<EditText>(R.id.editTextUrl) }
    val editTextVideo: EditText by lazy { findViewById<EditText>(R.id.editTextVideo) }
    lateinit var tipo: String
    override fun onCreate(savedInstanceState: Bundle?) {

        val context = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peticion_aniadir_animales)
        tipo = "aves"
        spinnerTipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        tipo = "aves"
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
                    4 ->{
                        tipo = "reptiles"
                        Toast.makeText(context,"reptiles",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    // var tipo:String, var nombre:String, var foto:String, var descripcion:String, var video:String
    fun clickAniadir(view: View) {

        /*   spinnerTipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
               override fun onItemSelected(
                   parent: AdapterView<*>?,
                   view: View?,
                   position: Int,
                   id: Long
               ) {
                   when (position){
                       0->tipo="aves"
                       1->tipo="insectos"
                       2->tipo="mamifero"
                       3->tipo="peces"
                       4->tipo="reptiles"
                   }
               }

               override fun onNothingSelected(parent: AdapterView<*>?) {
   */
        //   }
        // }
        var nombre: String = editTextNombreAnimal.text.toString()
        var fotoUrl: String = editTextUrl.text.toString()
        var descripcion = editTextDescripcion.text.toString()
        var video: String = editTextVideo.text.toString()
        val animal: Animal = Animal(tipo, nombre, fotoUrl, descripcion, video)
        Database.firebaseDB.collection("peticionAnimales").document(animal.nombre).set(animal)
            .addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Peticion hecha correctamente", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            "animal no insertado en la colleccion",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        Toast.makeText(this, "" + animal, Toast.LENGTH_LONG).show()

    }
}