package com.nicolasfernandez.elmundoanimal.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.fragments.Inicio


class ListViewPrueba(val fragmentActivity: Inicio,val datos: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return datos.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = fragmentActivity.layoutInflater


        val vista: View = inflater.inflate(R.layout.elementos_tipos_especie, null)

        val cuadroTexto: TextView = vista.findViewById(R.id.txtNombre)

        cuadroTexto.text = datos.get(position)

        val icono = vista.findViewById<ImageView>(R.id.imgEspecie)




        establecerIcono(icono, datos.get(position))



        return vista
    }


    fun establecerIcono(icono: ImageView, nombre: String) {
        when (nombre) {
            "aves" -> icono.setImageResource(R.drawable.aves)
            "peces" -> icono.setImageResource(R.drawable.peces)
            "mamifero" -> icono.setImageResource(R.drawable.mamifero)
            "reptiles" -> icono.setImageResource(R.drawable.reptiles)
            "insectos" -> icono.setImageResource(R.drawable.insectos)
        }
    }

}