package com.nicolasfernandez.elmundoanimal.recycler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.FichaAnimal
import com.nicolasfernandez.elmundoanimal.clases.Animal

class SeccionAnimalesAdapter (val contexto:Activity, val datos:ArrayList<Animal>):RecyclerView.Adapter<SeccionAnimalesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeccionAnimalesViewHolder {
       return SeccionAnimalesViewHolder(contexto.layoutInflater.inflate(R.layout.elementos_seccion_animales,parent,false))

    }

    override fun onBindViewHolder(holder: SeccionAnimalesViewHolder, position: Int) {
    holder.txtNombreAnimalSeccion.text=datos.get(position).nombre

        Glide.with(holder.itemView.context).load(datos.get(position).foto).into(holder.imgAnimal2)

        holder.cardSeccion.setOnClickListener {
            var bundle: Bundle? = Bundle()
            bundle?.putString("fichaSeccionAnimal", datos.get(position).nombre)
            var actividadFicha: Intent = Intent(contexto, FichaAnimal::class.java)
            bundle?.putString("fichaEspecieAnimal", datos.get(position).tipo)
            if (bundle != null) {
                actividadFicha.putExtras(bundle)
            }

            contexto.startActivity(actividadFicha)
        }
    }

    override fun getItemCount(): Int {
            return datos.size
    }

}