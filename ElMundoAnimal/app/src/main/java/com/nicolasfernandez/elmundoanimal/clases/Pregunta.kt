package com.nicolasfernandez.elmundoanimal.clases

class Pregunta(val descripcion:String,val imagen:String, val a:String,val b:String,val c:String, val d:String,val correcto:String,val id:String) {
    constructor():this("","","","","","","","")

    override fun toString(): String {
        return "Pregunta(descripcion='$descripcion', imagen='$imagen', a='$a', b='$b', c='$c', d='$d', correcto='$correcto', id='$id')"
    }


}