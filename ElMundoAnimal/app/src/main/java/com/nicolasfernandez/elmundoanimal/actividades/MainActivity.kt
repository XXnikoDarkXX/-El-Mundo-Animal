package com.nicolasfernandez.elmundoanimal.actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.RuntimeExecutionException

import com.google.firebase.auth.GoogleAuthProvider
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.constantes.Database

import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth

/**
 * Clase inicial al empezar la aplicacion desde aquí podremos realizar actividades de login/registro de la app
 */
class MainActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 123 // Request code for signing in with Google
    private val btnGoogle:Button by lazy { findViewById<Button>(R.id.inicioGoogle) }//Referencia al boton para login google
    private val btnEmail:Button by lazy { findViewById<Button>(R.id.inicioEmail) }//Referencia al botón para login con email
    private val txtInfo:TextView by lazy { findViewById<TextView>(R.id.txtCuenta) }//Referencia  no tienes cuenta
    private val txtRegistro:TextView by lazy { findViewById<TextView>(R.id.txtRegistro) }//variable privata txt del registro
    private val txtRecuperarContrasenia:TextView by lazy { findViewById<TextView>(R.id.txtRecuperarContrasenia) }//txt de contraseña
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verificarLoginUsuario()

    }

    /**
     * Funcion para ir a la actividad de Registro
     * @param view vista en este caso será un botón
     */
    fun irRegistro(view: View) {


        startActivity(Intent(this, Registro::class.java))

    }

    /**
     * Funcion para llamar al intent de login con google usando nuestro toquen configurado
     * @param view vista en este caso un boton
     */
    fun loginGoogle(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("365537516760-o045mide23gu6dsq8pef9up1avtsjnfj.apps.googleusercontent.com")
                .requestEmail()
                .build()

      val  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val intentGoogleSignIn=mGoogleSignInClient.signInIntent
        startActivityForResult(intentGoogleSignIn, GOOGLE_SIGN_IN)

    }

    /**
     * Funcion para realizar el login con google mediante un request codigo
     * @param requestCode codigo el cual se compara con GOOGLE_SIGN_IN
     * @param resultCode igualmente hacemos la misma comprobacion que requestCode
     * @param data datos
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            try {
                val taskSignIn=GoogleSignIn.getSignedInAccountFromIntent(data)
                val googleAccount=taskSignIn.result
                    firebaseAuthWithGoogle(googleAccount!!)

            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } catch (runtimeExecution: RuntimeExecutionException) {
                // This exception happens when the user doesn't choose a google account from the Google account chooser
                Log.d("errorException", runtimeExecution.message!!)
            }
        }
    }

    /**
     * Funcion para autenticar el login con firebaser usando auth de google
     * @param googleAccount cuenta la cual hemos hecho el login
     * Si el login fue correcto tendremos en nuestra contante Database firebaseAuth la cuenta logeada
     */
    private fun firebaseAuthWithGoogle(googleAccount: GoogleSignInAccount) {
        val credential= GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,""+googleAccount.email.toString(),Toast.LENGTH_LONG).show()

                        verificarLoginUsuario()
                    } else {
                        Toast.makeText(this@MainActivity, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
    }

    /**
     * Funcion para ir a la actividad de Login Email
     * @param view vista en este caso el botón
     */
    fun irALogInEmail(view: View) {

        startActivity(Intent(this, LoginEmail::class.java))

    }

    /**
     * Funcion para cuando abra la app verifique que esta logueado
     * Mediante esta función comprobamos si existe el usuario comprobandole en la bbdd o ya esta logueada y pasaría a la pantalla
     * de carga
     */
    fun verificarLoginUsuario(){

        if(firebaseAuth.currentUser!=null) {
            val docRef = Database.firebaseDB.collection("usuarios")
                .document(firebaseAuth.currentUser.email.toString())
            docRef.get().addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(Usuario::class.java)
                if (user != null) {
                    val actividadCarga:Intent= Intent(this,Carga::class.java)
                    var bundle:Bundle=Bundle()//iniciamos la variable
                    bundle.putString("Principal","Principal")

                    actividadCarga.putExtras(bundle)
                    this.startActivity(actividadCarga)
                }else{
                    startActivity(Intent(this, CrearNombreNickGoogle::class.java))

                }
            }
        }else{
            btnGoogle.visibility=View.VISIBLE
            btnEmail.visibility=View.VISIBLE
            txtInfo.visibility=View.VISIBLE
            txtRegistro.visibility=View.VISIBLE
           txtRecuperarContrasenia.visibility=View.VISIBLE
        }
    }

    /**
     * Función para recuperar la contrasenia de la cuenta
     * @param view vista en este caso un boton
      */
    fun clickRecuperarContrasenia(view: View) {
        startActivity(Intent(this, RecuperarContrasenia::class.java))


    }
}