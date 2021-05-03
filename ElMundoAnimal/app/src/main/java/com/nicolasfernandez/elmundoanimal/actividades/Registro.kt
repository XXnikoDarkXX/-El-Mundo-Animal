package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB

class Registro : AppCompatActivity() {
    val nombre:EditText by lazy { findViewById<EditText>(R.id.nombreCompleto) }
    val nickname:EditText by lazy { findViewById<EditText>(R.id.nickname) }
    val email:EditText by lazy { findViewById<EditText>(R.id.loginEmail) }
    val contrasenia:EditText by lazy { findViewById<EditText>(R.id.loginContraseña) }
    val repiteContrasenia:EditText by lazy { findViewById<EditText>(R.id.repiteContrasenia) }
   lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth= FirebaseAuth.getInstance()
    }

    fun Registrar(view: View) {

        val ema:String=email.text.toString()
        val pass:String =contrasenia.text.toString()
        val contexto=this;
        if (nombre.text.toString().equals("")||nickname.text.toString().equals("")||email.text.toString().equals("")||
                contrasenia.text.toString().equals("")||repiteContrasenia.text.toString().equals("")){
            Toast.makeText(this,"algun campo esta vacio",Toast.LENGTH_LONG).show()
        }else{
            //Si tenemos la contraseña pues creamos el usuario
            if (contrasenia.text.toString().equals(repiteContrasenia.text.toString())){

              firebaseAuth.createUserWithEmailAndPassword(ema, pass)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(contexto,"usuario creado correctamente",Toast.LENGTH_LONG).show()
                                val user = auth.currentUser

                                val usuario:Usuario=Usuario(nombre.text.toString(),nickname.text.toString(),email.text.toString(),contrasenia.text.toString(),0)
                                firebaseDB.collection("usuarios").document(email.text.toString()).set(usuario).addOnCompleteListener(this,
                                    OnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText( contexto,"Insertado correctamente", Toast.LENGTH_LONG).show()
                                        }else{
                                            Toast.makeText( contexto,"usuario no insertado en la colleccion", Toast.LENGTH_LONG).show()
                                        }
                                    })
                              startActivity(Intent(this, MainActivity::class.java))


                            } else {


                                Toast.makeText(contexto,"usuario no creado",Toast.LENGTH_LONG).show()


                            }
                        }




            }else{
                Toast.makeText(contexto,"Contraseñas no coinciden",Toast.LENGTH_LONG).show()

            }







        }



    }
}