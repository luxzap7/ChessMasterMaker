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

    private val testQuestions = mutableListOf<TestQuestion>()

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

        loadQuestions()

        displayQuestion()

        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        option1Button.setOnClickListener { checkAnswer(0) }
        option2Button.setOnClickListener { checkAnswer(1) }
        option3Button.setOnClickListener { checkAnswer(2) }

        val favoriteButton = findViewById<ImageView>(R.id.favoriteButton)
        val currentTestId = "test_1"
        checkIfFavorite(currentTestId, favoriteButton)

        favoriteButton.setOnClickListener {
            toggleFavorite(currentTestId, favoriteButton)
        }
    }

    data class TestQuestion(
        val imageUri: Uri? = null,
        val imageRes: Int? = null,
        val correctOption: Int
    )

    private fun loadQuestions() {
        testQuestions.addAll(predefinedQuestions)

        testQuestions.addAll(CreateTestActivity.newlyCreatedTasks)
    }

    private fun displayQuestion() {
        val currentQuestion = testQuestions[currentQuestionIndex]

        val chessBoardImage = findViewById<ImageView>(R.id.chessBoardImage)
        if (currentQuestion.imageUri != null) {
            chessBoardImage.setImageURI(currentQuestion.imageUri)
        } else if (currentQuestion.imageRes != null) {
            chessBoardImage.setImageResource(currentQuestion.imageRes)
        }

        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        option1Button.text = "1"
        option2Button.text = "2"
        option3Button.text = "3"
    }

    private fun checkAnswer(selectedOption: Int) {
        if (selectedOption == testQuestions[currentQuestionIndex].correctOption) {
            score++
        }

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
