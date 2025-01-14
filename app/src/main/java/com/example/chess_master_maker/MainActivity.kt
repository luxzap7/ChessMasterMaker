package com.example.chess_master_maker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startTrainingButton = findViewById<Button>(R.id.startTrainingButton)
        startTrainingButton.setOnClickListener {
            // Otvara ekran s opcijama
            val intent = Intent(this, OptionActivity::class.java)
            startActivity(intent)
        }
    }
}
