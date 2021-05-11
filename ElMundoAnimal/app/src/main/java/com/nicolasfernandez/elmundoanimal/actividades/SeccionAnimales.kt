package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.recycler.ControlPeticionAdapter
import com.nicolasfernandez.elmundoanimal.recycler.SeccionAnimalesAdapter

class SeccionAnimales : AppCompatActivity() {
    val recyclerSeccionAnimales: RecyclerView by lazy { findViewById(R.id.recyclerSeccion) }
    val txtNombreSeccion:TextView by lazy { findViewById<TextView>(R.id.txtNombreSeccion) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seccion_animales)
        var bundle:Bundle?=this.intent.extras
        var especie:String?=bundle?.getString("Tipo")
        if (especie!=null){
            txtNombreSeccion.text=this.resources.getString(R.string.txtSeccionAnimales).toString()+especie
            traerPeticionesAnimales(especie)
        }

    }
    fun traerPeticionesAnimales( especie:String){
        val context = this
        var animales: ArrayList<Animal> = ArrayList<Animal>()
        Database.firebaseDB.collection(especie).get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val animal = document.toObject(Animal::class.java)

                    animales.add(animal)

                }

                val adapter: SeccionAnimalesAdapter = SeccionAnimalesAdapter(this,animales)
                recyclerSeccionAnimales.adapter=adapter
                recyclerSeccionAnimales.layoutManager= LinearLayoutManager(context)

            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }
    }



}