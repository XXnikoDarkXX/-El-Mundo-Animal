package actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database.Companion.firebaseAuth

class LoginEmail : AppCompatActivity() {
    val email:EditText by lazy { findViewById<EditText>(R.id.loginEmail) }
    val contrasenia:EditText by lazy { findViewById<EditText>(R.id.loginContraseña) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)
    }

    fun login(view: View) {
        val contexto=this
        firebaseAuth.signInWithEmailAndPassword(email.text.toString(), contrasenia.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(contexto,"Usuario logeado correctamente",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, Principal::class.java))
                }else{
                    Toast.makeText(contexto,"Has especificado una contraseña o email incorrecto",Toast.LENGTH_LONG).show()
                }
            }


    }
}