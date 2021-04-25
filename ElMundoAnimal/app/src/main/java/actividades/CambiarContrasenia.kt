package actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import clases.Usuario
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database
import constantes.Database.Companion.firebaseAuth

class CambiarContrasenia : AppCompatActivity() {
    val linearCarga:LinearLayout by lazy { findViewById<LinearLayout>(R.id.linearCarga) }
    val cardView11:CardView by lazy { findViewById<CardView>(R.id.cardView11) }
    val cardView12:CardView by lazy { findViewById<CardView>(R.id.cardView12) }
    val btnCambiar:Button by lazy { findViewById<Button>(R.id.btnCambiar) }
    val txtInfoCambiar:TextView by lazy { findViewById<TextView>(R.id.txtInfoCambiar) }
    val editPassActual:TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassActual) }
    val editPassNueva:TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva) }
    val editPassNueva2:TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.editPassNueva2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasenia)








    }

    fun Cambiar(view: View) {
        val user = firebaseAuth.currentUser

        var usuario:Usuario = Usuario()

        val docRef = Database.firebaseDB.collection("usuarios").document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {

                usuario=user

            }
        }

        if (editPassActual.text.toString().equals(usuario.contrasenia)&&editPassNueva.text.toString().equals(editPassNueva2.text.toString())){
            user!!.updatePassword(editPassNueva.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                    Toast.makeText(this,"Has cambiado la contrasenia correctamente",Toast.LENGTH_LONG).show()
                        usuario.contrasenia=editPassNueva.text.toString()

                        Database.firebaseDB.collection("usuarios").document(usuario.email).set(usuario).addOnCompleteListener(this,
                            OnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show()
                                }else{

                                }
                            })
                    }
                }
        }


    }


}