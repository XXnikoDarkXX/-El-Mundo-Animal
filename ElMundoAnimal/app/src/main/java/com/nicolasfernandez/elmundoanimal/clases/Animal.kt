package com.nicolasfernandez.elmundoanimal.clases

class Animal (var tipo:String, var nombre:String, var foto:String, var descripcion:String, var video:String) {
    constructor():this("","","","","")

    override fun toString(): String {
        return "Animal(tipo='$tipo', nombre='$nombre', foto='$foto', descripcion='$descripcion', video='$video')"
    }
}