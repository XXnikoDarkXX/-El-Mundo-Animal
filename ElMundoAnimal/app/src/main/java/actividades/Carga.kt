package actividades

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nicolasfernandez.elmundoanimal.R

class Carga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carga)
        var bundle: Bundle? = this.intent.extras
        var info1:String?=bundle?.getString("Principal")
        var info2:String?=bundle?.getString("Login")

        if (info1.equals("Principal")){
            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this, Principal::class.java))
            }, 3000)



        }
        if (info2.equals("Login")){
            val handler = Handler()
            handler.postDelayed({
                startActivity(Intent(this, Principal::class.java))
            }, 3000)

        }
    }
}