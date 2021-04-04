package actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicolasfernandez.elmundoanimal.R
import fragments.Inicio
import java.lang.reflect.Array.newInstance

class Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val inicio = Inicio.newInstance()
        openFragment(inicio)
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationInicio -> {
                val inicio = Inicio.newInstance()
                openFragment(inicio)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationJugar -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationPerfil -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Funcion para abrir fragmentos desde el menu
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}