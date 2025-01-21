package com.example.chess_master_maker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        listView = findViewById(R.id.listView)
        firestore = FirebaseFirestore.getInstance()

        fetchFavorites()
    }

    private fun fetchFavorites() {
        firestore.collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                val favoriteItems = mutableListOf<String>()

                for (document in documents) {
                    val title = document.getString("title") ?: "Untitled"
                    favoriteItems.add(title)
                }

                if (favoriteItems.isEmpty()) {
                    Toast.makeText(this, "No favorites found", Toast.LENGTH_SHORT).show()
                } else {
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_list_item_1,
                        favoriteItems
                    )
                    listView.adapter = adapter
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load favorites", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
    }
}
