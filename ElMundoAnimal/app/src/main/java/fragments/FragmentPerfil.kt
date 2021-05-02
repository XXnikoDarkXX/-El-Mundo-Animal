package fragments

import actividades.CambiarContrasenia
import actividades.CambiarDatosUsuario
import actividades.MainActivity
import actividades.PurebaConsultaFirebase
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database.Companion.firebaseAuth
import constantes.Database.Companion.firebaseDB

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentPerfil.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentPerfil : Fragment() {
    lateinit var txtNombrePerfil :TextView
    lateinit var txtNicknamePerfil:TextView
    lateinit var txtEmailPerfil: TextView
    lateinit var usuario:Usuario
    lateinit var cardView7:CardView
    lateinit var cardView8:CardView
    lateinit var cardView9:CardView
    lateinit var cardView10:CardView
    lateinit var pgCarga:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
     */
    fun finalizarSesion(){
        firebaseAuth.signOut()

    }

    /**
     * Funcion para recoger los datos de un usuario de la bbdd
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