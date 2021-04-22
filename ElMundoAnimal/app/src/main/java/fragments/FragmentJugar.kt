package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database.Companion.firebaseAuth
import constantes.Database.Companion.firebaseDB
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

                    val user= document.toObject(Usuario::class.java)

                    jugadores.add(user)

                }
                val array= rankingTop5(jugadores)
                val adapter: ListViewRankingTop = ListViewRankingTop(this,array)
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


    fun rankingTop5(jugadores: ArrayList<Usuario>):Array<Usuario> {
        val array:Array<Usuario>  = Array(5) { i -> Usuario() }

        for (i in 0..jugadores.size - 1) {

            for (j in 0..jugadores.size - 1) {
                if (!(j == jugadores.size)) {
                    if (jugadores.get(i).ranking > jugadores.get(j).ranking) {
                        comprobarArray5(array, jugadores.get(i))
                        break
                    }
                }

            }
        }
        return array
    }

    /**
     * Funcion para comprobar si jugador tiene mayor puntuacion que los jugadores del array
     * @param jugador jugador que vamos a comprobar que tenga mas puntuacion que los jugadores del array
     * @param array de Usuario donde contenemos a los jugadores con mayor ranking en el momento)
     */
    fun comprobarArray5(array: Array<Usuario>,jugador :Usuario){


        for (i  in 0.. array.size-1){

            if (array[i].nombre.equals("")){//si es null el espacio del array metemos al jugador
                array[i.toInt()]=jugador
                break
            }
            //Comprobamos que jugador no este ya en el array y si no lo esta lo metemos en caso de que tenga mayor puntuacion
            //que algun jugador del array

            if (jugador.ranking>array[i].ranking){

                comprobarArray5(array,array[i.toInt()])
                array[i.toInt()]= jugador
                break
            }


        }
    }

}