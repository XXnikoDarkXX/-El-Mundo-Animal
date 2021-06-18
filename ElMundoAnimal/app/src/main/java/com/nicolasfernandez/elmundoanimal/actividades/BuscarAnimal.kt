package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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

/**
 * Actividad para poder buscar un animal de la base de datos y cargarlo en una lista
 * Recibe por bundle el nombre que el usuario ha escrito y comprueba en la bbdd similitudes y por ultimo lo muestra en un recycler
 *
 * */
class BuscarAnimal : AppCompatActivity() {
    val AnimalesEncontrados:ArrayList<Animal> = ArrayList<Animal>()//array list con los animales encontrados
    val recyclerBuscador: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerBuscador) }//Lista que usaremos para mostrar los animales
    val buscador: EditText by lazy { findViewById<EditText>(R.id.buscador) }//Buscador que usaremos para recoger la palabras de animal a mostrar
    lateinit var listaAnimales:ArrayList<Animal>//Lista total de animales de la bbdd usaremos esto pues es mas rápido consultar una vez la
    //base de datos que estar todo rato haciéndolo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_animal)
        var bundle: Bundle? = this.intent.extras
        var nombreAnimalBuscado: String? = bundle?.getString("animalBuscado")

         listaAnimales = bundle?.getSerializable("animales") as ArrayList<Animal>



        if (nombreAnimalBuscado != null) {

            buscarAnimal(nombreAnimalBuscado)
        }


    }

    /**
     * Funcion para cuando hagamos click al boton llame a buscarAnimal()
     * @param view vista en este caso un boton
     */
    fun clickBuscar(view: View) {
        if (!buscador.text.toString().equals("")) {
            buscarAnimal(buscador.text.toString())
        }


    }

    /**
     * Funcion para encontrar los posibles animales que el usuario ha escrito
     * @param nombreAnimalBuscado el nombre que el usuario ha escrito iremos comprobando la lista de animales y en caso de que coincidan
     * las palabras lo metermos en otra lista de animales encontrados. Finalmente mostraremos esta última en un recycler
     */
    fun buscarAnimal(nombreAnimalBuscado:String){
        AnimalesEncontrados.clear()
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