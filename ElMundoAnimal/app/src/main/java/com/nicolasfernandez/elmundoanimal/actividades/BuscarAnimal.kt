package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Pregunta
import com.nicolasfernandez.elmundoanimal.constantes.APIConstantes
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.recycler.ControlPeticionAdapter
import com.nicolasfernandez.elmundoanimal.recycler.SeccionAnimalesAdapter

class BuscarAnimal : AppCompatActivity() {
    val AnimalesEncontrados:ArrayList<Animal> = ArrayList<Animal>()
    val recyclerBuscador: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerBuscador) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_animal)
        var bundle: Bundle? = this.intent.extras
        var nombreAnimalBuscado: String? = bundle?.getString("animalBuscado")

        var listaAnimales:ArrayList<Animal> = bundle?.getSerializable("animales") as ArrayList<Animal>



        if (nombreAnimalBuscado != null) {
            //buscarAnimal(nombreAnimalBuscado)


            /* Handler(Looper.getMainLooper()).postDelayed({
                 if (!animalEncontrado) {
                     startActivity(Intent(this, Principal::class.java))
                     Toast.makeText(this,"Animal no encontrado", Toast.LENGTH_LONG).show()
                 }
             }, 3000)*/
          //  buscarAnimal(nombreAnimalBuscado)
            for (animal in listaAnimales){

                if (animal.nombre.toLowerCase().contains(nombreAnimalBuscado.toLowerCase())&&animal.nombre.toLowerCase().get(0).equals(nombreAnimalBuscado.get(0))){

                    AnimalesEncontrados.add(animal)
                }

            }


            val adapter: SeccionAnimalesAdapter = SeccionAnimalesAdapter(this,AnimalesEncontrados)
            recyclerBuscador.adapter=adapter
            recyclerBuscador.layoutManager= LinearLayoutManager(this)

        }


    }

    fun buscarAnimal(nombre: String) {

        val array: ArrayList<String> = ArrayList()
        array.add("aves")
        array.add("insectos")
        array.add("peces")
        array.add("reptiles")
        array.add("mamifero")

        val listaAnimales: ArrayList<Animal> = ArrayList<Animal>()


        for (especie in array) {


            Database.firebaseDB.collection(especie).get().addOnSuccessListener { result ->

                for (document in result) {

                    var animal = document.toObject(Animal::class.java) as Animal

                    if (animal.nombre.contains(nombre)) {

                        Toast.makeText(this, ""+animal.nombre, Toast.LENGTH_LONG).show()
                        listaAnimales.add(animal)
                    }
                }

            }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "" + exception, Toast.LENGTH_LONG).show()
                    //   Log.d(TAG, "Error getting documents: ", exception)
                }


        }

        val adapter: SeccionAnimalesAdapter = SeccionAnimalesAdapter(this,listaAnimales)
        recyclerBuscador.adapter=adapter
        recyclerBuscador.layoutManager= LinearLayoutManager(this)



    }


}