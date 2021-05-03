package com.nicolasfernandez.elmundoanimal.clases

class Usuario (var nombre:String, var nickname:String,val email:String, var contrasenia:String,var ranking:Int){


    constructor():this("","","","",0)

    override fun toString(): String {
       return "Nombre: "+nombre+" nickname: "+nickname+" email: "+email+" contraseña: "+contrasenia
    }
}