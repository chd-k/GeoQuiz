package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

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
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        setButtonsClickable(quizViewModel.isAnswerChecked)
    }

    private fun nextQuestion() {
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun prevQuestion() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId: Int
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_snackbar
            quizViewModel.putToAnswerMap(1)
        } else {
            messageResId = R.string.incorrect_snackbar
            quizViewModel.putToAnswerMap(0)
        }

        Snackbar.make(
            binding.root,
            messageResId,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()

        setButtonsClickable(quizViewModel.isAnswerChecked)

        checkGrades()
    }

    private fun checkGrades() {
        val score = quizViewModel.computeGrades()
        if (score != 0) {
            Toast.makeText(
                this,
                "Your score $score %!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setButtonsClickable(clickable: Boolean) {
        binding.trueButton.isClickable = clickable
        binding.falseButton.isClickable = clickable
    }
}