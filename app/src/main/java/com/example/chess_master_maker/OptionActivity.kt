package com.example.chess_master_maker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        val playButton = findViewById<Button>(R.id.playButton)
        playButton.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        val makeNewButton = findViewById<Button>(R.id.makeNewButton)
        makeNewButton.setOnClickListener {
            val intent = Intent(this, CreateTestActivity::class.java)
            startActivity(intent)
        }

        val favoriteFolder = findViewById<ImageView>(R.id.favoriteFolder)
        favoriteFolder.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}
