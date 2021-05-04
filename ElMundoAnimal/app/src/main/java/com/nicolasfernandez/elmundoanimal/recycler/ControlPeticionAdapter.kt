package com.nicolasfernandez.elmundoanimal.recycler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.FichaAnimal
import com.nicolasfernandez.elmundoanimal.clases.Animal

class ControlPeticionAdapter(val contexto:Activity,val datos:ArrayList<Animal>):RecyclerView.Adapter<ControlPeticionesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ControlPeticionesViewHolder {
        return ControlPeticionesViewHolder(
            contexto.layoutInflater.inflate(R.layout.elementos_control_peticiones,
                parent,false)) }

    override fun onBindViewHolder(holder: ControlPeticionesViewHolder, position: Int) {


        holder.txtNombreAnimal.text=datos.get(position).nombre

        Glide.with(holder.itemView.context)
            .load(datos.get(position).foto)
            .into(holder.imgFotoAnimal)
        holder.imgFotoAnimal.setOnClickListener {
         var bundle: Bundle? = Bundle()
            var nombreAnimal:String? =datos.get(position).nombre
            bundle?.putString("ficha",nombreAnimal)
            var actividadFicha:Intent = Intent(contexto,FichaAnimal::class.java)
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