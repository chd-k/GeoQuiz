package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

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

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var answersMap = mutableMapOf<Int, Int>()
    private var cheatingMap = mutableMapOf<Int, Boolean>()

    var isAnswerChecked: Boolean = true
        private set

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val isCurrentQuestionCheated: Boolean
        get() = cheatingMap[currentQuestionText] ?: false

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        isAnswerChecked = true
        isCheater = false
    }

    fun moveToPrev() {
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
        isAnswerChecked = true
        isCheater = false
    }

    fun computeGrades(): Int {
        var score = 0
        if (answersMap.size == questionBank.size || answersMap.size + cheatingMap.size == questionBank.size) {
            score = 100 * answersMap.values.sum() / questionBank.size
            answersMap.clear()
            cheatingMap.clear()
        }
        return score
    }

    fun putToAnswerMap(value: Int) {
        this.isAnswerChecked = false
        answersMap[currentQuestionText] = value
    }

    fun captureCheating() {
        cheatingMap[currentQuestionText] = true
    }
}