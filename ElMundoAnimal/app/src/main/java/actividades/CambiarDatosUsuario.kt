package actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import clases.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database
import constantes.Database.Companion.firebaseAuth
import constantes.Database.Companion.firebaseDB

class CambiarDatosUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_datos_usuario)
    }


    /**
     * Funcion para cambiar datos del perfil
     */
    fun cambiar(view: View) {
        val nickname:EditText = findViewById<EditText>(R.id.editTextNickname)
        val nombre:EditText= findViewById<EditText>(R.id.editTextNombre)

        var usuario: Usuario = Usuario()
        val docRef = Database.firebaseDB.collection("usuarios")
            .document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {

                usuario = user
                usuario.nickname=nickname.text.toString()
                usuario.nombre=nombre.text.toString()
                insertarOActualizarUsuario(usuario)

            }
        }

    }
    fun eliminar(view: View) {
        val pass1:EditText = findViewById<EditText>(R.id.editTextPassActual)
        val pass2:EditText=findViewById<EditText>(R.id.editTextPassActual2)
        var usuario: Usuario = Usuario()

        val docRef = Database.firebaseDB.collection("usuarios")
            .document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            var user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {
                usuario = user
                if (pass1.text.toString().equals(pass2.text.toString()) && pass1.text.toString()
                        .equals(usuario.contrasenia)) {

                    var user: FirebaseUser = firebaseAuth.currentUser

                    val credential: AuthCredential =
                        EmailAuthProvider.getCredential(user.email, pass1.text.toString())


                    user?.reauthenticate(credential)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Reautenticiacion completa", Toast.LENGTH_LONG)
                                .show()

                    user?.delete().addOnCompleteListener {
                        if(it.isSuccessful){
                            borrarUsuario(usuario)
                        }
                    }
                        }
                    }
                }
            }

        }


    }

    /**
     * Funcion para insertar o acutalizar en la bbdd (firestore) el usuario pasado por parametros
     */
    fun insertarOActualizarUsuario(usuario: Usuario) {

        Database.firebaseDB.collection("usuarios").document(usuario.email).set(usuario).addOnCompleteListener(this,
            OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText( this,"Insertado/Actualizado correctamente", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Principal::class.java))

                }else{
                    Toast.makeText( this,"usuario no insertado/actualizado en la colleccion", Toast.LENGTH_LONG).show()
                }
            })



    }
    fun borrarUsuario(usuario: Usuario){
        firebaseDB.collection("usuarios").document(usuario.email).delete().addOnCompleteListener {
            Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_LONG)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}