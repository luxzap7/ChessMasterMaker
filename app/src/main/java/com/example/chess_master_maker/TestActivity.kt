package com.example.chess_master_maker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class TestActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var isFavorite = false
    private var currentQuestionIndex = 0
    private var score = 0

    // Lista pitanja, uključujući i predefinirana i korisnički dodana
    private val testQuestions = mutableListOf<TestQuestion>()

    // Predefinirana pitanja
    private val predefinedQuestions = listOf(
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_1, correctOption = 0),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_2, correctOption = 1),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_3, correctOption = 2),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_4, correctOption = 0),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_5, correctOption = 1),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_6, correctOption = 2),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_7, correctOption = 0),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_8, correctOption = 1),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_9, correctOption = 2),
        TestQuestion(imageUri = null, imageRes = R.drawable.test_image_10, correctOption = 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        firestore = FirebaseFirestore.getInstance()

        // Učitaj predefinirana i korisnički kreirana pitanja
        loadQuestions()

        // Inicijalni prikaz prvog pitanja
        displayQuestion()

        // Gumbovi za odgovore
        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        option1Button.setOnClickListener { checkAnswer(0) }
        option2Button.setOnClickListener { checkAnswer(1) }
        option3Button.setOnClickListener { checkAnswer(2) }

        // Gumb za favorite
        val favoriteButton = findViewById<ImageView>(R.id.favoriteButton)
        val currentTestId = "test_1" // Jedinstveni ID za test
        checkIfFavorite(currentTestId, favoriteButton)

        favoriteButton.setOnClickListener {
            toggleFavorite(currentTestId, favoriteButton)
        }
    }

    data class TestQuestion(
        val imageUri: Uri? = null, // URI za korisničke slike
        val imageRes: Int? = null, // Resurs za predefinirane slike
        val correctOption: Int
    )

    // Učitaj predefinirana i korisnički kreirana pitanja
    private fun loadQuestions() {
        // Dodaj predefinirana pitanja
        testQuestions.addAll(predefinedQuestions)

        // Dodaj korisnički kreirane zadatke
        testQuestions.addAll(CreateTestActivity.newlyCreatedTasks)
    }

    private fun displayQuestion() {
        val currentQuestion = testQuestions[currentQuestionIndex]

        // Postavi sliku
        val chessBoardImage = findViewById<ImageView>(R.id.chessBoardImage)
        if (currentQuestion.imageUri != null) {
            chessBoardImage.setImageURI(currentQuestion.imageUri)
        } else if (currentQuestion.imageRes != null) {
            chessBoardImage.setImageResource(currentQuestion.imageRes)
        }

        // Postavite opcije za gumbove (opcionalno)
        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        option1Button.text = "1"
        option2Button.text = "2"
        option3Button.text = "3"
    }

    private fun checkAnswer(selectedOption: Int) {
        // Provjeri odgovor
        if (selectedOption == testQuestions[currentQuestionIndex].correctOption) {
            score++
        }

        // Pređi na sljedeće pitanje ili prikaži rezultate
        if (currentQuestionIndex < testQuestions.size - 1) {
            currentQuestionIndex++
            displayQuestion()
        } else {
            showResults()
        }
    }

    private fun checkIfFavorite(testId: String, button: ImageView) {
        firestore.collection("favorites").document(testId).get()
            .addOnSuccessListener { document ->
                isFavorite = document.exists()
                button.setImageResource(
                    if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_empty
                )
            }
    }

    private fun toggleFavorite(testId: String, button: ImageView) {
        val favoriteRef = firestore.collection("favorites").document(testId)

        if (isFavorite) {
            favoriteRef.delete().addOnSuccessListener {
                isFavorite = false
                button.setImageResource(R.drawable.ic_heart_empty)
            }
        } else {
            val favoriteData = hashMapOf(
                "image" to testQuestions[currentQuestionIndex].imageRes.toString(),
                "timestamp" to System.currentTimeMillis()
            )
            favoriteRef.set(favoriteData).addOnSuccessListener {
                isFavorite = true
                button.setImageResource(R.drawable.ic_heart_filled)
            }
        }
    }

    private fun showResults() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Rezultati")
            .setMessage("Točno: $score/${testQuestions.size}")
            .setPositiveButton("Vrati se na početak") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("Pokušaj ponovno") { _, _ ->
                recreate()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }
}
