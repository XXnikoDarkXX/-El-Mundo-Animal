package com.nicolasfernandez.elmundoanimal.actividades

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB

/**
 * Actividad para poder cambiar datos del usuario asi como poder borrarlo
 */
class CambiarDatosUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_datos_usuario)
    }


    /**
     * Funcion para cambiar datos del perfil consultando a la bbdd
     * @param view vista en este caso el boton
     * /tenemos varios filtros en caso de que solo queramos cambiar un dato o esten los campos vacios.
     */
    fun cambiar(view: View) {


        val nickname: EditText = findViewById<EditText>(R.id.editTextNickname)
        val nombre: EditText = findViewById<EditText>(R.id.editTextNombre)

        var usuario: Usuario = Usuario()
        val docRef = Database.firebaseDB.collection("usuarios")
            .document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {

                usuario = user
                if(!(nickname.text.toString().equals("")&&nombre.text.toString().equals(""))){
                    usuario.nickname = nickname.text.toString()
                    usuario.nombre = nombre.text.toString()
                    insertarOActualizarUsuario(usuario)
                }else if (!nickname.text.toString().equals("")){
                    usuario.nickname = nickname.text.toString()

                    insertarOActualizarUsuario(usuario)
                    }else if(!nombre.text.toString().equals("")){
                    usuario.nombre = nombre.text.toString()
                    insertarOActualizarUsuario(usuario)
                }else{
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Campos vacios")
                    builder.setMessage("Uno o mas campos estan vacios")
                    builder.setPositiveButton("Aceptar", null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }



            }
        }

    }

    /**
     * Funcion para insertar o acutalizar en la bbdd (firestore) el usuario pasado por parametros
     * @param usuario Usuario al que vamos a insertar o actualizar
     */
    fun insertarOActualizarUsuario(usuario: Usuario) {

        Database.firebaseDB.collection("usuarios").document(usuario.email).set(usuario)
            .addOnCompleteListener(this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Insertado/Actualizado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this, Principal::class.java))

                    } else {
                        Toast.makeText(
                            this,
                            "usuario no insertado/actualizado en la colleccion",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })


    }

    /**
     * Funcion para borrar Usuario
     * @param usuario objeto de tipo Usuario que vamos a borrar de la bbdd
     */
    fun borrarUsuario(usuario: Usuario) {
        firebaseDB.collection("usuarios").document(usuario.email).delete().addOnCompleteListener {
            Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_LONG)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    /**
     * Funcion para eliminar un usuario de nuestra aplicacion mediante diferentes consultas
     * Para ello hemos comprobado que las contraseñas puestas en las actividad sean las mismas que el usuario al que vamos a borrar
     * Tambien comprobamos las credenciales del usuario para luego hacer un borrado
     * @param view vista en este caso un boton
     */
    fun eliminar(view: View) {
        val pass1: EditText = findViewById<EditText>(R.id.editTextPassActual)
        val pass2: EditText = findViewById<EditText>(R.id.editTextPassActual2)
        var usuario: Usuario = Usuario()


        if(pass1.text.toString().equals("")||pass2.text.toString().equals("")){
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Campos vacios")
            builder.setMessage("Uno o mas campos estan vacios")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }else {

            val docRef = Database.firebaseDB.collection("usuarios")
                .document(firebaseAuth.currentUser.email.toString())
            docRef.get().addOnSuccessListener { documentSnapshot ->
                var user = documentSnapshot.toObject(Usuario::class.java)
                if (user != null) {
                    usuario = user
                    if (pass1.text.toString().equals(pass2.text.toString())
                        && firebaseAuth.currentUser.email.toString().equals(usuario.email)
                    ) {


                        var user: FirebaseUser = firebaseAuth.currentUser

                        val credential: AuthCredential =
                            EmailAuthProvider.getCredential(user.email, pass1.text.toString())


                        user?.reauthenticate(credential)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Reautenticiacion completa", Toast.LENGTH_LONG)
                                    .show()

                                user?.delete().addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        borrarUsuario(usuario)
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "La contraseña no es correcta",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                    } else {
                        Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }

    }


}

