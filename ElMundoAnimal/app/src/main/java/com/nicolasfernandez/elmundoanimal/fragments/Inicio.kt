package com.nicolasfernandez.elmundoanimal.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.*
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.recycler.ListViewTiposEspecies
import com.nicolasfernandez.elmundoanimal.recycler.SeccionAnimalesAdapter


/**
 *
 * Fragment del inicio donde mostraremos la actividad de wikipedia
 *
 */
class Inicio : Fragment() {
    lateinit var  listaAnimales:ArrayList<Animal>//arrayList de animales
    lateinit var btnAyuda: Button;//btn para ir ayuda de la aplicacion
    lateinit var btnAniadirAnimales:Button//btn para ir añadir animales
    lateinit var btnControl:Button// btn para ir al control de animales
    lateinit var buscador:EditText//buscador
    lateinit var btnBuscar:Button//Btn para buscar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    /**
     * Desde aqui mostraremos y iniciamos las diferentes actividades que hay en inicio
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)
        listaAnimales= ArrayList<Animal>()
        btnAyuda = view.findViewById(R.id.btnIrAyuda) as Button
        btnAniadirAnimales= view.findViewById<Button>(R.id.btnAniadirAnimales)
        btnControl=view.findViewById<Button>(R.id.btnControl)
        buscador=view.findViewById<EditText>(R.id.buscador)
        btnBuscar=view.findViewById<Button>(R.id.btnBuscar)
        obtenerTodosLosAnimales()

        btnAyuda.setOnClickListener(View.OnClickListener {



            val intent = Intent(activity, ActividadAyuda::class.java)
            startActivity(intent)

        })

        btnBuscar.setOnClickListener {
            var bundle: Bundle?= Bundle()
            if (!buscador.text.toString().equals("")) {
                bundle?.putString("animalBuscado", buscador.text.toString())
                bundle?.putSerializable("animales",listaAnimales)

              val intent = Intent(activity, BuscarAnimal::class.java)
            if (bundle!=null){
                intent.putExtras(bundle)
            }
             startActivity(intent)
            }else{
                Toast.makeText(view.context,"No has escrito nada",Toast.LENGTH_LONG).show()
            }




        }



        btnControl.setOnClickListener(View.OnClickListener {


            val fragmentJuego = FragmentLoginAdministrador.newInstance()
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.container, fragmentJuego)
                transaction.addToBackStack(null)
                transaction.commit()

            }

        })

        btnAniadirAnimales.setOnClickListener(View.OnClickListener {


            val intent = Intent(activity, PeticionAniadirAnimales::class.java)
            startActivity(intent)

        })
        val datos: ArrayList<String> = ArrayList<String>()
        datos.add("aves")
        datos.add("insectos")
        datos.add("mamifero")
        datos.add("peces")
        datos.add("reptiles")

        //Con el listView
        val adapter:ListViewTiposEspecies= ListViewTiposEspecies(this, datos)
        val lista:ListView = view.findViewById(R.id.lista)

        lista.adapter=adapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment Inicio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            Inicio().apply {
                arguments = Bundle().apply {

                }
            }
    }

    /**
     * Funcon para obtener todos los animales de las diferentes colecciones de la bbdd
     * lo insertaremos en la lista listaAnimales
     */
    fun obtenerTodosLosAnimales() {

        val array: ArrayList<String> = ArrayList()
        array.add("aves")
        array.add("insectos")
        array.add("peces")
        array.add("reptiles")
        array.add("mamifero")




        for (especie in array) {


            Database.firebaseDB.collection(especie).get().addOnSuccessListener { result ->

                for (document in result) {

                    var animal = document.toObject(Animal::class.java) as Animal



                        listaAnimales.add(animal)


                }

            }
                .addOnFailureListener { exception ->

                    //   Log.d(TAG, "Error getting documents: ", exception)
                }


        }





    }

}