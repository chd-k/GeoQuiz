package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    private var answersMap = mutableMapOf<Int, Int>()

    var isAnswerChecked: Boolean = true
        private set

    val currentQuestionAnswer: Boolean
        get() {
            this.isAnswerChecked = false
            return questionBank[currentIndex].answer
        }

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isAnswerChecked = true
    }

    fun moveToPrev() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
        isAnswerChecked = true
    }

    fun computeGrades(): Int {
        var score = 0
        if (answersMap.size == questionBank.size) {
            score = 100 * answersMap.values.sum() / questionBank.size
            answersMap.clear()
        }
        return score
    }

    fun putToAnswerMap(value: Int) {
        answersMap[currentQuestionText] = value
    }
}