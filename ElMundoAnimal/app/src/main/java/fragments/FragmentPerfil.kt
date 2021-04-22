package fragments

import actividades.MainActivity
import actividades.PurebaConsultaFirebase
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
            }
        }

    }


}