package com.nicolasfernandez.elmundoanimal.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfernandez.elmundoanimal.R

/**
 * Control peticiones view holder
 *
 * @constructor
 *
 * @param itemView
 */
class ControlPeticionesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtNombreAnimal:TextView  by lazy { itemView.findViewById<TextView>(R.id.txtNombreAnimal2) }
    val imgFotoAnimal:ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgFotoAnimal) }
    val imgAniadir:ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgAniadir) }
    val imgCancelar:ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgCancelar) }





}