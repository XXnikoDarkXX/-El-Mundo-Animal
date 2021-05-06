package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB
import com.nicolasfernandez.elmundoanimal.recycler.ControlPeticionAdapter

 class ControlPeticionesAnimales : AppCompatActivity() {
    val recylerControl:RecyclerView by lazy { findViewById<RecyclerView>(R.id.recylerControl) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_peticiones_animales)

        traerPeticionesAnimales()

        /*val imgFoto:ImageView= findViewById<ImageView>(R.id.imgFotoAnimal)
        Glide.with(this)
            .load("http://www.estudiantes.info/ciencias_naturales/images/pinguino-trio.png")
            .into(imgFoto)*/

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

    fun aniadirAnimalBBDD(animal:Animal){
        firebaseDB.collection(animal.tipo).document(animal.nombre).set(animal).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this,"Insertado correctamente", Toast.LENGTH_LONG).show()

                    borrarAnimalBBDD(animal)

                }else{
                    Toast.makeText( this,"error al insertar animal en la coleccion", Toast.LENGTH_LONG).show()
                }
            })
    }

    fun borrarAnimalBBDD(animal:Animal){
        firebaseDB.collection("peticionAnimales").document(animal.nombre).delete().addOnCompleteListener {
            Toast.makeText(this, "Animal Eliminado de peticion", Toast.LENGTH_LONG)
            traerPeticionesAnimales()
        }
    }

}