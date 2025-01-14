package com.example.chess_master_maker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class OptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        val playButton = findViewById<Button>(R.id.playButton)
        val makeNewButton = findViewById<Button>(R.id.makeNewButton)

        playButton.setOnClickListener {
            // Otvara ekran za odabir težine
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
        }

        makeNewButton.setOnClickListener {
            // Otvara ekran za kreiranje novog testa
            val intent = Intent(this, CreateTestActivity::class.java)
            startActivity(intent)
        }
    }
}
