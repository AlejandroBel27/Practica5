package mx.edu.itson.practica5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_pelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_pelicula)

        val iv_pelicula_imagen: ImageView = findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView = findViewById(R.id.tv_pelicula_desc)
        val buyTickets: Button = findViewById(R.id.buyTickets)

        val bundle = intent.extras
        if (bundle != null) {
            iv_pelicula_imagen.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.setText(bundle.getString("titulo"))
            tv_pelicula_desc.setText(bundle.getString("sinopsis"))
        }

        actualizarAsientos()

        buyTickets.setOnClickListener {
            val intent = Intent(this, SeatSelection::class.java)
            intent.putExtra("name", tv_nombre_pelicula.text.toString())
            intent.putExtra("pos", bundle?.getInt("pos") ?: -1)
            intent.putExtra("type", bundle?.getString("type") ?: "")
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarAsientos()
    }

    private fun actualizarAsientos() {
        val seatLeft: TextView = findViewById(R.id.seatLeft)
        val pos = intent.getIntExtra("pos", -1)
        val type = intent.getStringExtra("type")

        if (pos != -1) {
            val pelicula = if (type == "movie") {
                Catalogo.peliculas[pos]
            } else {
                Catalogo.series[pos]
            }
            val seatsAvailable = 20 - pelicula.seats.size
            seatLeft.setText("$seatsAvailable seats available")
        }
    }
}