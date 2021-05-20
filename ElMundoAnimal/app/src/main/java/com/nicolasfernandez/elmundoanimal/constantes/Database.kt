package com.nicolasfernandez.elmundoanimal.constantes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nicolasfernandez.elmundoanimal.clases.Pregunta

class Database {
 companion object  {
  val firebaseDB:FirebaseFirestore= FirebaseFirestore.getInstance()
  val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()




 }

}