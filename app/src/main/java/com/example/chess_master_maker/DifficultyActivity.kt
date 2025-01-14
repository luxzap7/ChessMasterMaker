package com.example.chess_master_maker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class DifficultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val easyButton = findViewById<Button>(R.id.easyButton)
        val mediumButton = findViewById<Button>(R.id.mediumButton)
        val hardButton = findViewById<Button>(R.id.hardButton)

        easyButton.setOnClickListener {
            // Pokreće testove za Easy težinu
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("difficulty", "easy")
            startActivity(intent)
        }

        mediumButton.setOnClickListener {
            // Pokreće testove za Medium težinu
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("difficulty", "medium")
            startActivity(intent)
        }

        hardButton.setOnClickListener {
            // Pokreće testove za Hard težinu
            val intent = Intent(this, TestActivity::class.java)
            intent.putExtra("difficulty", "hard")
            startActivity(intent)
        }
    }
}
