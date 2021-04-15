package actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import clases.Usuario
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.nicolasfernandez.elmundoanimal.R
import constantes.Database.Companion.firebaseAuth
import constantes.Database.Companion.firebaseDB
import recycler.ListViewR
import recycler.ListViewRankingTop

class PurebaConsultaFirebase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pureba_consulta_firebase)
    }

    fun consulta(view: View) {


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





        Toast.makeText(this,""+ firebaseAuth.currentUser.email,Toast.LENGTH_LONG).show()

    }




}

      /*  val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        var jugadores: ArrayList<Usuario> = ArrayList<Usuario>()

        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val user = document.toObject(Usuario::class.java)
                    jugadores.add(user)

                }
            }
            .addOnFailureListener { exception ->
                val excepcion = exception


            }



     /*   val selfRef=this;
        val docRef = firebaseDB.collection("usuarios").document("prueba@prueba.es")//Hacemos referencia a la coleccion
        //y al documento de la firebase
        val source = Source.CACHE//creamos un source para el cache
        docRef.get(source).addOnCompleteListener { task->

            if (task.isSuccessful){
                var resultados=task.result

                if (resultados!=null){

                    var hola=  resultados.data
                    var datos:ArrayList<String> = ArrayList<String>()

                    hola?.forEach{
                        it.key
                        it.value
                        datos.add(it.key+" : "+it.value)
                        Toast.makeText(selfRef,""+it.key+"  "+it.value,Toast.LENGTH_LONG).show()
                    }

                }
            }else{
                Toast.makeText(selfRef,"Vaya pasada que no funciona consultar",Toast.LENGTH_LONG).show()
            }
        }








*/

       */



