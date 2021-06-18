package com.nicolasfernandez.elmundoanimal.actividades

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth

/**
 * Actividad para poder cambiar la contraseña de login del usuario
 */
class CambiarContrasenia : AppCompatActivity() {

    val editPassActual: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassActual) }//Contraseña actual
    val editPassNueva: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva) }//contraseña nueva
    val editPassNueva2: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva2) }//Repiticion de la contraseña nueva


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasenia)


    }


    /**
     * Funcion para cambiar contraseña de un usuario, primera comprobando las credenciales (email con password)que esten correctas
     * Y finalmente cambiando a la nueva contraseña
     * @param view vista en este caso un boton
     * Contiene varios filtros en caso de que algun campo este vacio, la contraseña sea incorrecta....
     */
    fun Cambiar(view: View) {


        var user: FirebaseUser = firebaseAuth.currentUser

        if (editPassActual.text.toString().equals("") || editPassNueva2.text.toString().equals("") || editPassNueva.text.toString().equals("")) {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Campos vacios")
            builder.setMessage("Uno o mas campos estan vacios")
            builder.setPositiveButton("Aceptar", null)

            val dialog: AlertDialog = builder.create()
            dialog.show()

        } else if (editPassNueva.text.toString().equals(editPassNueva2.text.toString())) {


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
                                        this, "Contraseña Cambiada", Toast.LENGTH_LONG
                                    ).show()


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




