package com.nicolasfernandez.elmundoanimal.constantes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nicolasfernandez.elmundoanimal.clases.Animal
import com.nicolasfernandez.elmundoanimal.clases.Pregunta

/**
 * Clase para almacenar firebase db y firebase auth (usuario) de manera estatica
 */
class Database {
 companion object  {
  val firebaseDB:FirebaseFirestore= FirebaseFirestore.getInstance()//instancia de la bbdd para realizar consultas
  val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()//instancia del auth para el usuario


 }

}