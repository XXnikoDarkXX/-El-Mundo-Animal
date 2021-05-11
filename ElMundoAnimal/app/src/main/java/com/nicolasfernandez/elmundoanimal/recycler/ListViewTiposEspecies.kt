package com.nicolasfernandez.elmundoanimal.recycler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.SeccionAnimales
import com.nicolasfernandez.elmundoanimal.fragments.Inicio


class ListViewTiposEspecies(val fragmentActivity: Inicio, val datos: ArrayList<String>) : BaseAdapter() {
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


        val cardTipos=vista.findViewById<CardView>(R.id.cardTipos)

        cardTipos.setOnClickListener {
            var bundle: Bundle?= Bundle()
            bundle?.putString("Tipo",datos.get(position))
            var actividadSeccionAnimales:Intent = Intent(fragmentActivity.context, SeccionAnimales::class.java)
            if (bundle!=null){
                actividadSeccionAnimales.putExtras(bundle)
            }
            fragmentActivity.context?.startActivity(actividadSeccionAnimales)
        }

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