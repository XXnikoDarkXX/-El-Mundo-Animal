package com.nicolasfernandez.elmundoanimal.clases

import java.io.Serializable

/**
 * Clase animal el cual usaremos para operaciones crud y demás consultas que usen los animales
 * Contructor del animal con todos los campos
 * @param tipo tipo del animal
 * @param nombre nombre del animal
 * @param foto url de la foto del animal
 * @param descripcion descripcion del animal
 * @param video value del video de yt
 *
 */
class Animal (var tipo:String, var nombre:String, var foto:String, var descripcion:String, var video:String):Serializable {
    /**
     * Constructor vacio
     */
    constructor():this("","","","","")

    /**
     * Funcion para mostrar todos las variables del animal
     */
    override fun toString(): String {
        return "Animal(tipo='$tipo', nombre='$nombre', foto='$foto', descripcion='$descripcion', video='$video')"
    }
}