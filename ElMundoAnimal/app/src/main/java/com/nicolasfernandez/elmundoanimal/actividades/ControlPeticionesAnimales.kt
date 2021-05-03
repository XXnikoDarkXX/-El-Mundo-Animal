package com.nicolasfernandez.elmundoanimal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.recycler.ControlPeticionAdapter

class ControlPeticionesAnimales : AppCompatActivity() {
    val recylerControl:RecyclerView by lazy { findViewById<RecyclerView>(R.id.recylerControl) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_peticiones_animales)

        traerPeticionesAnimales()



    }
    fun traerPeticionesAnimales(){
        val context = this
        var animales: ArrayList<Animal> = ArrayList<Animal>()
        Database.firebaseDB.collection("peticionAnimales").get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val animal = document.toObject(Animal::class.java)

                    animales.add(animal)

                }

                val adapter:ControlPeticionAdapter= ControlPeticionAdapter(context,animales)
                recylerControl.adapter=adapter
                recylerControl.layoutManager=LinearLayoutManager(context)

            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }
    }


}