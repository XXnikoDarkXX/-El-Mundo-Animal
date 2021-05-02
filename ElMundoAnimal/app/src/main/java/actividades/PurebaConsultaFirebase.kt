package actividades

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import clases.Usuario
import com.nicolasfernandez.elmundoanimal.R


class PurebaConsultaFirebase : AppCompatActivity() {
    var jugadores: ArrayList<Usuario> = ArrayList<Usuario>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pureba_consulta_firebase)

        val emailIntent = Intent(Intent.ACTION_SEND)
        val CC = arrayOf("prueba@prueba.es")
        emailIntent.setData(Uri.parse("mailto:nicocheto1@gmail.com"));
        emailIntent.setType("Prueba de texto");
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Prueba de envio");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "There is no email client installed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun consulta(view: View) {
        val array = rankingTop5(jugadores)
        for (jugador in array) {
            Toast.makeText(this, "" + jugador.email, Toast.LENGTH_LONG).show()
        }
        //Consulta buena para recoger documentos en Firebase
        /*   var contador:Byte=0
        val datos:ArrayList<Usuario> = ArrayList<Usuario>()
        firebaseDB.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    contador++
                    //    Toast.makeText(this,""+document.data,Toast.LENGTH_LONG).show()
                    val articul= document.toObject(Usuario::class.java)
                    datos.add(articul)
                 //   Toast.makeText(this,""+articul,Toast.LENGTH_LONG).show()
                    println("")
                    //    Log.d(TAG, "${document.id} => ${document.data}")
                }

                val adapter: ListViewR = ListViewR(this,datos)
                val lista: ListView = findViewById(R.id.listaPrueba)

                lista.adapter=adapter
            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "Error getting documents: ", exception)
            }

*/


        //     Toast.makeText(this,""+ firebaseAuth.currentUser.email,Toast.LENGTH_LONG).show()

    }


    fun rankingTop5(jugadores: ArrayList<Usuario>): Array<Usuario> {
        val array: Array<Usuario> = Array(5) { i -> Usuario() }

        //   var contador:Int =1
        /* var contadorPrincipal:Byte =0

        for (jugador in jugadores) {


           for (i in contador..jugadores.size-1){

            if (jugador.ranking>jugadores.get(i).ranking){
            comprobarArray5(array,jugador)
                break
            }
               contador++
           }
          contadorPrincipal++
            contador=contadorPrincipal.toInt()+1
       }*/

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
    fun comprobarArray5(array: Array<Usuario>, jugador: Usuario) {


        for (i in 0..array.size - 1) {

            if (array[i].nombre.equals("")) {//si es null el espacio del array metemos al jugador
                array[i.toInt()] = jugador
                break
            }
            //Comprobamos que jugador no este ya en el array y si no lo esta lo metemos en caso de que tenga mayor puntuacion
            //que algun jugador del array

            if (jugador.ranking > array[i].ranking) {

                comprobarArray5(array, array[i.toInt()])
                array[i.toInt()] = jugador
                break
            }


        }
    }


}

