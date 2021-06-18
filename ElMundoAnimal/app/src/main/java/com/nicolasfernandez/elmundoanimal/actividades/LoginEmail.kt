package com.nicolasfernandez.elmundoanimal.actividades

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth

/**
 * Actividad para logearse en la aplicacion
 */
class LoginEmail : AppCompatActivity() {
    val email:EditText by lazy { findViewById<EditText>(R.id.loginEmail) }//editText email
    val contrasenia:EditText by lazy { findViewById<EditText>(R.id.loginContraseña) }//editText contraseña
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)
    }

    /**
     * Funcion login donde comprobaremos que los campos no esten vacios, que la contraseña sea la misma que las credenciales del usuario
     * tenemos varios filtros para ello, en caso de estar correcto pasaria a la pantalla de carga donde esta ultima recibirá un bundle
     * @param view boton
     */
    fun login(view: View) {

        if (email.text.toString().equals("")||contrasenia.text.toString().equals("")){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Campos vacios")
            builder.setMessage("Uno o mas campos estan vacios")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }else {

            val contexto = this
            firebaseAuth.signInWithEmailAndPassword(
                email.text.toString(),
                contrasenia.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(contexto, "Usuario logeado correctamente", Toast.LENGTH_LONG)
                            .show()

                        val actividadCarga: Intent = Intent(this, Carga::class.java)
                        var bundle: Bundle = Bundle()//iniciamos la variable
                        bundle.putString("Login", "Login")
                        actividadCarga.putExtras(bundle)
                        this.startActivity(actividadCarga)
                    } else {
                        Toast.makeText(
                            contexto,
                            "Has especificado una contraseña o email incorrecto",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }
    }
}