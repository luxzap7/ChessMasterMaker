package com.example.chess_master_maker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class TestActivity : AppCompatActivity() {

    private var score = 0
    private var currentTest = 1
    private val totalTests = 10

    data class Test(
        val image: Int, // Slika šahovske ploče za test
        val options: List<Int>, // Slike za opcije poteza
        val correctOption: Int // Index točne opcije (0, 1, ili 2)
    )

    private val tests = listOf(
        Test(
            image = R.drawable.sample_chess_board, // Šahovska ploča
            options = listOf(
                R.drawable.option1_image, // Opcija 1
                R.drawable.option2_image, // Opcija 2
                R.drawable.option3_image  // Opcija 3
            ),
            correctOption = 0 // Točna je prva opcija (Index 0)
        ),
        Test(
            image = R.drawable.sample_chess_board, // Drugi test (možeš zamijeniti slikama)
            options = listOf(
                R.drawable.option1_image,
                R.drawable.option2_image,
                R.drawable.option3_image
            ),
            correctOption = 1 // Točna je druga opcija (Index 1)
        )
        // Dodaj još testova po potrebi
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val testTitle = findViewById<TextView>(R.id.testTitle)
        val chessBoardImage = findViewById<ImageView>(R.id.chessBoardImage)
        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        val difficulty = intent.getStringExtra("difficulty")

        // Postavljanje početnog testa
        updateTest(testTitle, chessBoardImage, difficulty)

        // Klik za odabir opcija
        option1Button.setOnClickListener { checkAnswer(1) }
        option2Button.setOnClickListener { checkAnswer(2) }
        option3Button.setOnClickListener { checkAnswer(3) }
    }

    private fun updateTest(testTitle: TextView, chessBoardImage: ImageView, difficulty: String?) {
        val currentTestData = tests[currentTest - 1] // Dohvati podatke trenutnog testa

        testTitle.text = "Test $currentTest/${tests.size}"
        chessBoardImage.setImageResource(currentTestData.image) // Postavi sliku šahovske ploče

        // Prikaz opcija za poteze
        val option1Button = findViewById<Button>(R.id.option1Button)
        val option2Button = findViewById<Button>(R.id.option2Button)
        val option3Button = findViewById<Button>(R.id.option3Button)

        option1Button.setBackgroundResource(currentTestData.options[0])
        option2Button.setBackgroundResource(currentTestData.options[1])
        option3Button.setBackgroundResource(currentTestData.options[2])
    }


    private fun checkAnswer(selectedOption: Int) {
        val currentTestData = tests[currentTest - 1]

        if (selectedOption == currentTestData.correctOption) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            score++ // Dodaj bod
        } else {
            Toast.makeText(this, "Wrong answer.", Toast.LENGTH_SHORT).show()
        }

// Na kraju testa prikaži rezultat
        if (currentTest == tests.size) {
            Toast.makeText(this, "Test completed! Score: $score/${tests.size}", Toast.LENGTH_LONG).show()
            finish()
        }

    }

}

