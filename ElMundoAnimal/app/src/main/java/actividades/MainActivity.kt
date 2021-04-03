package actividades

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.RuntimeExecutionException

import com.google.firebase.auth.GoogleAuthProvider
import com.nicolasfernandez.elmundoanimal.R

import constantes.Database.Companion.firebaseAuth


class MainActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 123 // Request code for signing in with Google
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irRegistro(view: View) {


        startActivity(Intent(this, Registro::class.java))

    }

    fun loginGoogle(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("AIzaSyAisqBuYfru21RxqOTdEwW1gYzUfFtLh60")
                .requestEmail()
                .build()

      val  mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this)

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
                    } else {
                        Toast.makeText(this@MainActivity, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
    }

    fun irALogInEmail(view: View) {

        startActivity(Intent(this, LoginEmail::class.java))

    }
}