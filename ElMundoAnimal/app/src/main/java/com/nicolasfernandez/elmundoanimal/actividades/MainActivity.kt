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


class MainActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 123 // Request code for signing in with Google
    private val btnGoogle:Button by lazy { findViewById<Button>(R.id.inicioGoogle) }
    private val btnEmail:Button by lazy { findViewById<Button>(R.id.inicioEmail) }
    private val txtInfo:TextView by lazy { findViewById<TextView>(R.id.txtCuenta) }
    private val txtRegistro:TextView by lazy { findViewById<TextView>(R.id.txtRegistro) }
    private val txtRecuperarContrasenia:TextView by lazy { findViewById<TextView>(R.id.txtRecuperarContrasenia) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verificarLoginUsuario()
        Toast.makeText(this,""+Database.firebaseAuth.currentUser.email.toString(),Toast.LENGTH_LONG).show()
    }

    /**
     * Funcion para ir a la actividad de Registro
     */
    fun irRegistro(view: View) {


        startActivity(Intent(this, Registro::class.java))

    }

    fun loginGoogle(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("365537516760-o045mide23gu6dsq8pef9up1avtsjnfj.apps.googleusercontent.com")
                .requestEmail()
                .build()

      val  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val intentGoogleSignIn=mGoogleSignInClient.signInIntent
        startActivityForResult(intentGoogleSignIn, GOOGLE_SIGN_IN)

    }


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

    private fun firebaseAuthWithGoogle(googleAccount: GoogleSignInAccount) {
        val credential= GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,""+googleAccount.email.toString(),Toast.LENGTH_LONG).show()

                        val actividadCarga:Intent= Intent(this,Carga::class.java)
                        var bundle:Bundle=Bundle()//iniciamos la variable
                        bundle.putString("Principal","Principal")
                        actividadCarga.putExtras(bundle)
                        this.startActivity(actividadCarga)
                    } else {
                        Toast.makeText(this@MainActivity, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
    }

    /**
     * Funcion para ir a la actividad de Login Email
     */
    fun irALogInEmail(view: View) {

        startActivity(Intent(this, LoginEmail::class.java))

    }

    /**
     * Funcion para cuando abra la app verifique que esta logueado
     */
    fun verificarLoginUsuario(){
            if (firebaseAuth.currentUser!=null){
                val actividadCarga:Intent= Intent(this,Carga::class.java)
                var bundle:Bundle=Bundle()//iniciamos la variable
                bundle.putString("Principal","Principal")
                actividadCarga.putExtras(bundle)
                this.startActivity(actividadCarga)
            }
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

    fun clickRecuperarContrasenia(view: View) {
        startActivity(Intent(this, RecuperarContrasenia::class.java))


    }
}