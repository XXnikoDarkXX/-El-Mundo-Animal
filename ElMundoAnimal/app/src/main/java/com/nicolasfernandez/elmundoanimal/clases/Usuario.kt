package com.nicolasfernandez.elmundoanimal.clases


/**
 * Clase usuario, el cual usaremos para las diferentes operaciones crud y demás consultas relacionados a este
 *
 * Constructor con todos los parametros
 * @param nombre del usuario
 * @param nickname del usuario
 * @param email del usuario
 * @param ranking del usuario
 */
class Usuario (var nombre:String, var nickname:String,val email:String,var ranking:Int){

    /**
     * Constructor con todos los parametros vacios
     */
    constructor():this("","","",0)

    /**
     * Funcion para mostrar las variables de un usuario
     */
    override fun toString(): String {
       return "Nombre: "+nombre+" nickname: "+nickname+" email: "+email+" raking: "+ranking
    }
}