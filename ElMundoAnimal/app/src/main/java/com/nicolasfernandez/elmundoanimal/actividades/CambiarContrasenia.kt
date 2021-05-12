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

        var usuario: Usuario = Usuario()

        val docRef = Database.firebaseDB.collection("usuarios")
            .document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {

                usuario = user

                if (editPassActual.text.toString()
                        .equals(usuario.contrasenia) && editPassNueva.text.toString().equals(
                        editPassNueva2.text.toString()
                    )
                ) {
                    usuario.contrasenia=editPassNueva.text.toString()
                    var user: FirebaseUser = firebaseAuth.currentUser

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
                                            this,
                                            "Contraseña Cambiada",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        insertarOActualizarUsuario(usuario)

                                    }

                                }
                        }
                    }

                } else {
                    Toast.makeText(
                        this,
                        "Algunos de los campos estan incorrectos",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }


    }

    /**
     * Funcion para insertar o acutalizar en la bbdd (firestore) el usuario pasado por parametros
     */
    fun insertarOActualizarUsuario(usuario: Usuario) {

        firebaseDB.collection("usuarios").document(usuario.email).set(usuario).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Toast.makeText( this,"Insertado/Actualizado correctamente", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Principal::class.java))

                }else{
                    Toast.makeText( this,"usuario no insertado/actualizdo en la colleccion", Toast.LENGTH_LONG).show()
                }
            })



    }


}