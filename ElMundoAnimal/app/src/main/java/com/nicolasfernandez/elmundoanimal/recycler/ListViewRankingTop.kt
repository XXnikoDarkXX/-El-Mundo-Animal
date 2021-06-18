package com.nicolasfernandez.elmundoanimal.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.fragments.FragmentJugar

/**
 * List view para mostrar el top 5 de usuarios del fragment jugar
 * @param fragmentActivity Fragment de tipo jugar
 * @param datos array con los usuarios a mostrar
 */
    class ListViewRankingTop(val fragmentActivity: FragmentJugar, val datos: Array<Usuario>) : BaseAdapter() {
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


        val vista: View = inflater.inflate(R.layout.elementos_mejores_jugadores, null)

        val nickname:TextView =vista.findViewById<TextView>(R.id.txtNombreAnimal2)
        nickname.text=datos.get(position).nickname
        val txtPuntuacion:TextView=vista.findViewById<TextView>(R.id.txtPuntuacion)
        val posicion:Byte = (1 + position).toByte()
        txtPuntuacion.text=datos.get(position).ranking.toString()+" puntos -->"+posicion+" Posición"

        return vista
    }
}