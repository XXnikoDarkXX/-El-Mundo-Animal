package com.nicolasfernandez.elmundoanimal.clases

/**
 *
 * Clase que se usara para la actividad juego desde aqui podremos obtener todas las preguntas de la bbdd como obj Pregunta
 *
 * @param descripcion descripcion de la pregunta
 * @param imagen url de la imagen
 * @param a boton a
 * @param boton b
 * @param c boton
 * @param correcto boton que es correcto
 * @param id de la pregunta
 *
 */
class Pregunta(val descripcion:String,val imagen:String, val a:String,val b:String,val c:String, val d:String,val correcto:String,val id:String) {
    /*
    Constructor vacio
     */
    constructor():this("","","","","","","","")

    /**
     * Funcion para mostrar las variables de la pregunta
     */
    override fun toString(): String {
        return "Pregunta(descripcion='$descripcion', imagen='$imagen', a='$a', b='$b', c='$c', d='$d', correcto='$correcto', id='$id')"
    }


}