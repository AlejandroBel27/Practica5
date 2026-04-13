package mx.edu.itson.practica5

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title: TextView = findViewById(R.id.titleSeats)
        var posMovie = -1
        var type = ""

        val bundle = intent.extras
        if (bundle != null) {
            title.setText(bundle.getString("name"))
            posMovie = bundle.getInt("pos")
            type = bundle.getString("type") ?: ""
        }

        val confirm: Button = findViewById(R.id.confirm)
        val row1: RadioGroup = findViewById(R.id.row1)
        val row2: RadioGroup = findViewById(R.id.row2)
        val row3: RadioGroup = findViewById(R.id.row3)
        val row4: RadioGroup = findViewById(R.id.row4)

        confirm.setOnClickListener {
            val selectedId1 = row1.checkedRadioButtonId
            val selectedId2 = row2.checkedRadioButtonId
            val selectedId3 = row3.checkedRadioButtonId
            val selectedId4 = row4.checkedRadioButtonId

            val finalId = when {
                selectedId1 != -1 -> selectedId1
                selectedId2 != -1 -> selectedId2
                selectedId3 != -1 -> selectedId3
                selectedId4 != -1 -> selectedId4
                else -> -1
            }

            if (finalId != -1) {
                val radioButton: RadioButton = findViewById(finalId)
                val seatNumber = radioButton.text.toString().toInt()

                // Agregar el cliente a la lista global
                val cliente = Cliente("Cliente", "Efectivo", seatNumber)
                if (type == "movie") {
                    Catalogo.peliculas[posMovie].seats.add(cliente)
                } else if (type == "series") {
                    Catalogo.series[posMovie].seats.add(cliente)
                }

                Toast.makeText(this, "Enjoy the movie! Seat #$seatNumber reserved.", Toast.LENGTH_LONG).show()
                finish() // Cerrar la pantalla
            } else {
                Toast.makeText(this, "Please select a seat", Toast.LENGTH_SHORT).show()
            }
        }

        // Variable para evitar recursión infinita al limpiar RadioGroups
        var isClearing = false

        row1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId > -1 && !isClearing) {
                isClearing = true
                row2.clearCheck()
                row3.clearCheck()
                row4.clearCheck()
                isClearing = false
            }
        }
        row2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId > -1 && !isClearing) {
                isClearing = true
                row1.clearCheck()
                row3.clearCheck()
                row4.clearCheck()
                isClearing = false
            }
        }
        row3.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId > -1 && !isClearing) {
                isClearing = true
                row1.clearCheck()
                row2.clearCheck()
                row4.clearCheck()
                isClearing = false
            }
        }
        row4.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId > -1 && !isClearing) {
                isClearing = true
                row1.clearCheck()
                row2.clearCheck()
                row3.clearCheck()
                isClearing = false
            }
        }
    }
}