package com.nicolasfernandez.elmundoanimal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.nicolasfernandez.elmundoanimal.R
import com.nicolasfernandez.elmundoanimal.actividades.ControlPeticionesAnimales
import com.nicolasfernandez.elmundoanimal.constantes.APIConstantes

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLoginAdministrador.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLoginAdministrador : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var txtInputUser: TextInputEditText
    lateinit var txtInputPassAdmin:TextInputEditText
    lateinit var btnAceptar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_login_administrador, container, false)
        if (APIConstantes.userAdmin){
            val intent = Intent(activity, ControlPeticionesAnimales::class.java)
            startActivity(intent)
        }
        btnAceptar = view.findViewById(R.id.btnAceptar) as Button
        txtInputUser=view.findViewById(R.id.txtInputUser) as TextInputEditText
        txtInputPassAdmin=view.findViewById(R.id.txtInputPassAdmin) as TextInputEditText

        btnAceptar.setOnClickListener {
            if (txtInputUser.text.toString().equals("admin")&&txtInputPassAdmin.text.toString().equals("admin")){

                APIConstantes.userAdmin=true
                  val intent = Intent(activity, ControlPeticionesAnimales::class.java)
                 startActivity(intent)
                Toast.makeText(view.context, "Contraseña correcta bienvenido Administrador :D", Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(view.context, "Algunos de los campos es incorrecto reviselo", Toast.LENGTH_LONG).show()

            }
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
         * @return A new instance of fragment FragmentLoginAdministrador.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentLoginAdministrador().apply {
                arguments = Bundle().apply {


                }
            }
    }
}