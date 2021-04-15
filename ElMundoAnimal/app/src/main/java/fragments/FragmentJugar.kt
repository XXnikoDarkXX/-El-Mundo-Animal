package fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import clases.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database
import constantes.Database.Companion.firebaseAuth
import constantes.Database.Companion.firebaseDB
import recycler.ListViewPrueba
import recycler.ListViewRankingTop


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentJugar.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentJugar : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_jugar, container, false)




        //ListView para coger los primeros 5 usuarios

        var jugadores: ArrayList<Usuario> = ArrayList<Usuario>()
        firebaseDB.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val articul= document.toObject(Usuario::class.java)

                    jugadores.add(articul)

                }

                val adapter: ListViewRankingTop = ListViewRankingTop(this,jugadores)
                val lista: ListView = view.findViewById(R.id.listaMejoresJugadores)

                lista.adapter=adapter
                puntuacionJugador( view, jugadores)
            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }





        return view






    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentJugar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentJugar().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun puntuacionJugador( view: View, jugadores:ArrayList<Usuario>){

        for(jugador in jugadores){
            if (jugador.email.equals(firebaseAuth.currentUser.email)){
                val nombre: TextView = view.findViewById(R.id.nombreJugador)
                val nick: TextView = view.findViewById(R.id.nicknameJugador)

                val puntos: TextView = view.findViewById(R.id.txtPuntuacionJuego)

                nombre.text="Nombre: " +jugador.nombre
                nick.text="Nickname: "+jugador.nickname
                puntos.text= "Puntuacion: "+jugador.ranking +" puntos"
            }
        }
    }


}