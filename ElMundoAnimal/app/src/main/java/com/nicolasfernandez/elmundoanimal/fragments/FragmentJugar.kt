package com.nicolasfernandez.elmundoanimal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.firebase.firestore.Query
import com.nicolasfernandez.elmundoanimal.clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.AyudaJuego
import com.nicolasfernandez.elmundoanimal.actividades.ControlPeticionesAnimales
import com.nicolasfernandez.elmundoanimal.actividades.Juego
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseAuth
import com.nicolasfernandez.elmundoanimal.constantes.Database.Companion.firebaseDB
import com.nicolasfernandez.elmundoanimal.recycler.ListViewRankingTop


/**
 * Fragment de Jugar
 * Aqui tendremos la informacion y menu del juego como la puntuacion del jugador, informacion de ayuda del juego, asi como los
 * 5 mejores jugadores en puntaucion
 *
 */
class FragmentJugar : Fragment() {

    lateinit var cardView2: CardView//un cardview

    lateinit var pgCarga2:ProgressBar//un loading
    lateinit var scrollJugar:ScrollView//un scroll para las card
    lateinit var btnJugar:Button//btn para ir al juego
    lateinit var btnInfoJuego:Button//btn para ir a la ayuda del juego

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


        btnInfoJuego=view.findViewById<Button>(R.id.btnInfoJuego)
        cardView2 = view.findViewById<CardView>(R.id.cardView2)
        scrollJugar=view.findViewById<ScrollView>(R.id.scrollJugar)
        btnJugar=view.findViewById<Button>(R.id.btnJugar)
        scrollJugar.visibility=View.GONE

        val jugadores:Array<Usuario>  = Array(5) { i -> Usuario() }
        firebaseDB.collection("usuarios")
            .limit(5).orderBy("ranking",Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->
                var contador:Int=0;
                for (document in result) {

                    val user= document.toObject(Usuario::class.java) as Usuario

                    jugadores[contador]=user
                    contador++

                }

               val adapter: ListViewRankingTop = ListViewRankingTop(this,jugadores)
               val lista: ListView = view.findViewById(R.id.listaMejoresJugadores)

                lista.adapter=adapter
                puntuacionJugador( view, jugadores)
                pgCarga2=view.findViewById(R.id.pgCarga2)
                pgCarga2.visibility=View.GONE
                cardView2.visibility=View.VISIBLE

                scrollJugar.visibility=View.VISIBLE

            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }


        btnInfoJuego.setOnClickListener{
            val intent = Intent(activity, AyudaJuego::class.java)
            startActivity(intent)        }
            btnJugar.setOnClickListener {
                val intent = Intent(activity, Juego::class.java)
                startActivity(intent)
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

    /**
     * Funcion para obtener la puntuacion del jugador logueado
     * @param jugadores lista de jugadores
     */
    fun puntuacionJugador( view: View, jugadores:Array<Usuario>){
        val docRef = firebaseDB.collection("usuarios").document(firebaseAuth.currentUser.email.toString())
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val user = documentSnapshot.toObject(Usuario::class.java)
            if (user != null) {
                if (user.email.equals(firebaseAuth.currentUser.email)) {
                    val nombre: TextView = view.findViewById(R.id.nombreJugador)
                    val nick: TextView = view.findViewById(R.id.nicknameJugador)

                    val puntos: TextView = view.findViewById(R.id.txtPuntuacionJuego)

                    nombre.text = "Nombre: " + user.nombre
                    nick.text = "Nickname: " + user.nickname
                    puntos.text = "Puntuacion: " + user.ranking + " puntos"
                }
            }
        }
    }


}