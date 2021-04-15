package recycler

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import fragments.FragmentJugar

class ListViewR(val fragmentActivity: Activity, val datos: ArrayList<Usuario>) : BaseAdapter() {
    override fun getCount(): Int {
       return datos.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    //    return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = fragmentActivity.layoutInflater


        val vista: View = inflater.inflate(R.layout.elementos_mejores_jugadores, null)

        Toast.makeText(fragmentActivity,""+datos.get(position),Toast.LENGTH_LONG).show()
        val nickname:TextView =vista.findViewById<TextView>(R.id.txtnickname)
        nickname.text=datos.get(position).nickname
        val txtPuntuacion:TextView=vista.findViewById<TextView>(R.id.txtPuntuacion)
        txtPuntuacion.text=datos.get(position).ranking.toString()
        Toast.makeText(fragmentActivity,""+datos.get(position),Toast.LENGTH_LONG).show()
        return vista
    }
}