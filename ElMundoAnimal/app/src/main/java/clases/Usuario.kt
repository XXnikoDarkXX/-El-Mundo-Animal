package clases

class Usuario (val nombre:String, val nickname:String,val email:String, val contrasenia:String,val ranking:Int){


    constructor():this("","","","",0)

    override fun toString(): String {
       return "Nombre: "+nombre+" nickname: "+nickname+" email: "+email+" contraseña: "+contrasenia
    }
}