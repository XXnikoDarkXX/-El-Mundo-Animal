package com.nicolasfernandez.elmundoanimal.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nicolasfernandez.elmundoanimal.R

/**
 * Holder con todas las variables a mostar en la lista
 */
class SeccionAnimalesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    val cardSeccion:CardView by lazy { itemView.findViewById<CardView>(R.id.cardSeccion) }
    val txtNombreAnimalSeccion:TextView by lazy { itemView.findViewById<TextView>(R.id.txtNombreAnimalSeccion) }
    val imgAnimal2:ImageView by lazy { itemView.findViewById<ImageView>(R.id.imgAnimal2) }


}