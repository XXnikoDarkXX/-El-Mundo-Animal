package com.nicolasfernandez.elmundoanimal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.ControlPeticionesAnimales
import com.nicolasfernandez.elmundoanimal.actividades.PeticionAniadirAnimales
import com.nicolasfernandez.elmundoanimal.recycler.ListViewPrueba


/**
 * A simple [Fragment] subclass.
 * Use the [Inicio.newInstance] factory method to
 * create an instance of this fragment.
 */
class Inicio : Fragment() {

    lateinit var btnPrueba: Button;
    lateinit var btnAniadirAnimales:Button
    lateinit var btnControl:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)

        btnPrueba = view.findViewById(R.id.btnIrAyuda) as Button
        btnAniadirAnimales= view.findViewById<Button>(R.id.btnAniadirAnimales)
        btnControl=view.findViewById<Button>(R.id.btnControl)

        btnPrueba.setOnClickListener(View.OnClickListener {

            Toast.makeText(view.context, "prueba", Toast.LENGTH_LONG).show()

            val intent = Intent(activity, ControlPeticionesAnimales::class.java)
            startActivity(intent)

        })


        btnControl.setOnClickListener(View.OnClickListener {



            val intent = Intent(activity, ControlPeticionesAnimales::class.java)
            startActivity(intent)

        })

        //TO DO CAMBIAR ESTO
        btnAniadirAnimales.setOnClickListener(View.OnClickListener {

            Toast.makeText(view.context, "prueba", Toast.LENGTH_LONG).show()

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
        val adapter:ListViewPrueba= ListViewPrueba(this, datos)
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




}