package com.nicolasfernandez.elmundoanimal.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
 * Fragment de login administrador, tendremos una validacion de formulario,
 * En caso de loguearnos nos lleva a la actividad controlPeticiones de  animales
 *
 */
class FragmentLoginAdministrador : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var txtInputUser: TextInputEditText//usuario
    lateinit var txtInputPassAdmin:TextInputEditText//contraseña
    lateinit var btnAceptar:Button//boton aceptar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    /**
     * Desde aqui comprobaremos y validaremos el formulario
     * mostraremos msj en caso de equivocarse con el login o lo llevaremos a la actividad de control
     */
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

            if (txtInputUser.text.toString().equals("")||txtInputPassAdmin.text.toString().equals("")){
                val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
                builder.setTitle("Campos vacios")
                builder.setMessage("Uno o mas campos estan vacios")
                builder.setPositiveButton("Aceptar", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }else {

                if (txtInputUser.text.toString()
                        .equals("admin") && txtInputPassAdmin.text.toString().equals("admin")
                ) {

                    APIConstantes.userAdmin = true
                    val intent = Intent(activity, ControlPeticionesAnimales::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        view.context,
                        "Contraseña correcta bienvenido Administrador :D",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(view.context, "campos incorrectos", Toast.LENGTH_LONG).show()

                }
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