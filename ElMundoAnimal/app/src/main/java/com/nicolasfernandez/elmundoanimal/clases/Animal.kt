package com.nicolasfernandez.elmundoanimal.clases

import java.io.Serializable

class Animal (var tipo:String, var nombre:String, var foto:String, var descripcion:String, var video:String):Serializable {
    constructor():this("","","","","")

    override fun toString(): String {
        return "Animal(tipo='$tipo', nombre='$nombre', foto='$foto', descripcion='$descripcion', video='$video')"
    }
}