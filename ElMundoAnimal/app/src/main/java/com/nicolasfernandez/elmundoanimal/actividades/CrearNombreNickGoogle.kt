package com.nicolasfernandez.elmundoanimal.actividades

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.constantes.Database

/**
 * Actividad para registrar nombre y nick en caso de loguearse por primera vez mediante google
 */
class CrearNombreNickGoogle : AppCompatActivity() {
    val editTextNickName: EditText by lazy { findViewById(R.id.editTextNickName) }//Editext donde recogeremos el nick del usuario
    val editTextNombre: EditText by lazy { findViewById(R.id.editTextNombre) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_nombre_nick_google)

    }

    /**
     * Funcion para registrar el nombre y el nick del usuario a la bbdd de firestore creando tambien un usuario con el correo
     * del usuario
     * @param view boton
     */
    fun clickRegistroGoogle(view: View) {

        if (editTextNickName.text.toString().equals("") || editTextNombre.text.toString()
                .equals("")
        ) {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Campos vacios")
            builder.setMessage("Uno o mas campos estan vacios")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()

        } else {

            val usuario: Usuario =
                Usuario(
                    editTextNombre.text.toString(),
                    editTextNickName.text.toString(),
                    Database.firebaseAuth.currentUser.email,
                    0
                )
            Database.firebaseDB.collection("usuarios")
                .document(Database.firebaseAuth.currentUser.email.toString()).set(usuario)
                .addOnCompleteListener(this,
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_LONG)
                                .show()
                            val actividadCarga: Intent = Intent(this, Carga::class.java)
                            var bundle: Bundle = Bundle()//iniciamos la variable
                            bundle.putString("Principal", "Principal")

                            actividadCarga.putExtras(bundle)
                            this.startActivity(actividadCarga)
                        } else {
                            Toast.makeText(
                                this,
                                "usuario no insertado en la colleccion",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
        }

    }
}