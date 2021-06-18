package com.nicolasfernandez.elmundoanimal.fragments

import com.nicolasfernandez.elmundoanimal.actividades.CambiarContrasenia
import com.nicolasfernandez.elmundoanimal.actividades.CambiarDatosUsuario
import com.nicolasfernandez.elmundoanimal.actividades.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.EnviarCorreo
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * Fragment de perfil el cual tendremos acceso a las diferentes opciones del usuario
 *
 */
class FragmentPerfil : Fragment() {
    lateinit var txtNombrePerfil :TextView//nombre del usuario
    lateinit var txtNicknamePerfil:TextView//nick del usuario
    lateinit var txtEmailPerfil: TextView//email del usuario
    lateinit var usuario:Usuario//el usuario
    lateinit var cardView7:CardView//card
    lateinit var cardView8:CardView//card
    lateinit var cardView9:CardView//card
    lateinit var cardView10:CardView//card
    lateinit var pgCarga:ProgressBar//loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    /**
     * Desde aqui usaremos las diferentes funciones / variables para mostrar las diferentes opciones
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_perfil, container, false)

        cardView7=view.findViewById<CardView>(R.id.cardView7)
        cardView8=view.findViewById<CardView>(R.id.cardView8)
        cardView9=view.findViewById<CardView>(R.id.cardView9)
        cardView10=view.findViewById<CardView>(R.id.cardView10)
        pgCarga=view.findViewById<ProgressBar>(R.id.pgCarga)
        pgCarga.visibility=View.VISIBLE
        cardView7.visibility=View.GONE
        cardView8.visibility=View.GONE
        cardView9.visibility=View.GONE
        cardView10.visibility=View.GONE
        txtNombrePerfil= view.findViewById<TextView>(R.id.txtNombreUsuario)
        txtNicknamePerfil=view.findViewById<TextView>(R.id.txtNicknamePerfil)
        txtEmailPerfil=view.findViewById<TextView>(R.id.txtEmailPerfil)

        traerUsuario()
        val btnFinalizarSession:Button = view.findViewById<Button>(R.id.btnFinalizarSesion)
        btnFinalizarSession.setOnClickListener {
            finalizarSesion()
            Toast.makeText(view.context, "Adios : "+usuario.nombre, Toast.LENGTH_LONG).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }



        val imgCambioPass:ImageView = view.findViewById<ImageView>(R.id.imgCambioPass)
        imgCambioPass.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, CambiarContrasenia::class.java)
            startActivity(intent)

        })
        cardView7.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, CambiarDatosUsuario::class.java)
            startActivity(intent)

        })

        val imgContacto:ImageView = view.findViewById<ImageView>(R.id.imgContacto)
        imgContacto.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, EnviarCorreo::class.java)
            startActivity(intent)

        })





        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FragmentPerfil.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentPerfil().apply {
                arguments = Bundle().apply {

                }
            }
    }

    /**
     * Funcion para finalizar la sesion del usuario
     * y volvemos a la primera ventana de la aplicacion
     */
    fun finalizarSesion(){
        firebaseAuth.signOut()

    }

    /**
     * Funcion para recoger los datos de un usuario de la bbdd
     * y mostraremos las diferentes opciones del fragmentPerfil
     */
    fun traerUsuario(){

        val docRef = firebaseDB.collection("usuarios").document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {
                txtNombrePerfil.text="Nombre: "+user.nombre
                txtNicknamePerfil.text="Nickname: "+user.nickname
                txtEmailPerfil.text="Email: "+ user.email
                usuario=user
                  pgCarga.visibility=View.GONE
                 cardView7.visibility=View.VISIBLE
                 cardView8.visibility=View.VISIBLE
                 cardView9.visibility=View.VISIBLE
                 cardView10.visibility=View.VISIBLE
            }
        }

    }


}