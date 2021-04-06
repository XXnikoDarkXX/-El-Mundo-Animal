package recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import clases.Usuario
import com.nicolasfernandez.elmundoanimal.R
import fragments.FragmentJugar

class ListViewRankingTop(val fragmentActivity: FragmentJugar,val datos: ArrayList<Usuario>) : BaseAdapter() {
    override fun getCount(): Int {
       return datos.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = fragmentActivity.layoutInflater


        val vista: View = inflater.inflate(R.layout.elementos_mejores_jugadores, null)

        val nickname:TextView =vista.findViewById<TextView>(R.id.txtnickname)
        nickname.text=datos.get(position).nickname
        val txtPuntuacion:TextView=vista.findViewById<TextView>(R.id.txtPuntuacion)
        txtPuntuacion.text=datos.get(position).ranking.toString()

        return vista
    }
}