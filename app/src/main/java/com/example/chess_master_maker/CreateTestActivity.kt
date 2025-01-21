    package com.example.chess_master_maker

    import android.app.Activity
    import android.content.Intent
    import android.net.Uri
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.ImageView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity

    class CreateTestActivity : AppCompatActivity() {

        private lateinit var selectImageButton: Button
        private lateinit var saveButton: Button
        private lateinit var imagePreview: ImageView
        private lateinit var correctOptionInput: EditText
        private var selectedImageUri: Uri? = null

        companion object {
            val newlyCreatedTasks = mutableListOf<TestActivity.TestQuestion>()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_create_test)

            selectImageButton = findViewById(R.id.selectImageButton)
            saveButton = findViewById(R.id.saveButton)
            imagePreview = findViewById(R.id.imagePreview)
            correctOptionInput = findViewById(R.id.correctOptionInput)

            selectImageButton.setOnClickListener { openImagePicker() }

            saveButton.setOnClickListener { saveTask() }
        }

        private fun openImagePicker() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1001)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
                selectedImageUri = data?.data
                imagePreview.setImageURI(selectedImageUri)
            }
        }

        private fun saveTask() {
            val correctOption = correctOptionInput.text.toString().toIntOrNull()

            if (selectedImageUri == null) {
                Toast.makeText(this, "Molimo odaberite sliku.", Toast.LENGTH_SHORT).show()
                return
            }

            if (correctOption == null || correctOption !in 1..3) {
                Toast.makeText(this, "Unesite točan odgovor (1-3).", Toast.LENGTH_SHORT).show()
                return
            }

            newlyCreatedTasks.add(
                TestActivity.TestQuestion(
                    imageUri = selectedImageUri,
                    correctOption = correctOption
                )
            )

            Toast.makeText(this, "Zadatak uspješno dodan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
