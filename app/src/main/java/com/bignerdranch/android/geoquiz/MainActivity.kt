package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

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
    private val answersMap = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        setButtonsClickable(true)
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
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_snackbar
            answersMap[questionBank[currentIndex].textResId] = 1
        } else {
            messageResId = R.string.incorrect_snackbar
            answersMap[questionBank[currentIndex].textResId] = 0
        }

        Snackbar.make(
            binding.root,
            messageResId,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()

        setButtonsClickable(false)

        checkGrades()
    }

    private fun checkGrades() {
        if (answersMap.size == questionBank.size) {
            Toast.makeText(
                this,
                "Your score ${100 * answersMap.values.sum() / questionBank.size} %!",
                Toast.LENGTH_SHORT
            ).show()
            answersMap.clear()
        }
    }

    private fun setButtonsClickable(clickable: Boolean) {
        binding.trueButton.isClickable = clickable
        binding.falseButton.isClickable = clickable
    }
}