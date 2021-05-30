package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.*
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB


class CambiarContrasenia : AppCompatActivity() {
    val linearCarga: LinearLayout by lazy { findViewById<LinearLayout>(R.id.linearCarga) }
    val cardView11: CardView by lazy { findViewById<CardView>(R.id.cardView11) }
    val cardView12: CardView by lazy { findViewById<CardView>(R.id.cardView12) }
    val btnCambiar: Button by lazy { findViewById<Button>(R.id.btnCambiar) }
    val txtInfoCambiar: TextView by lazy { findViewById<TextView>(R.id.txtLoginAdmin) }
    val editPassActual: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassActual) }
    val editPassNueva: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva) }
    val editPassNueva2: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasenia)


    }


    /**
     * Funcion para cambiar contraseña de un usuario, primera comprobando las credenciales (email con password)que esten correctas
     * Y finalmente cambiando a la nueva contraseña
     */
    fun Cambiar(view: View) {


        var user: FirebaseUser = firebaseAuth.currentUser



        if (editPassNueva.text.toString().equals(editPassNueva2.text.toString())) {

            val credential: AuthCredential =
                EmailAuthProvider.getCredential(user.email, editPassActual.text.toString())

            user?.reauthenticate(credential)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Reautenticiacion completa", Toast.LENGTH_LONG)
                        .show()
                    user?.updatePassword(editPassNueva.text.toString())
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this,"Contraseña Cambiada",Toast.LENGTH_LONG).show()



                            }
                        }


                } else {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG)
                        .show()
                }

            }
        } else {
            Toast.makeText(this, "Las contraseñas tienen que ser las mismas", Toast.LENGTH_LONG)
                .show()
        }
    }
}




