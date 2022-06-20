package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionTextView.setOnClickListener {
            nextQuestion()
        }
        binding.trueButton.setOnClickListener { checkAnswer(true) }
        binding.falseButton.setOnClickListener { checkAnswer(false) }
        binding.prevButton.setOnClickListener {
            prevQuestion()
        }
        binding.nextButton.setOnClickListener {
            nextQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun prevQuestion() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_snackbar
        } else {
            R.string.incorrect_snackbar
        }

        Snackbar.make(
            binding.root,
            messageResId,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }
}