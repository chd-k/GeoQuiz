package com.bignerdranch.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CHEATED_KEY = "CHEATED_KEY"

class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var cheated: Boolean
        get() = savedStateHandle.get(CHEATED_KEY) ?: false
        set(value) = savedStateHandle.set(CHEATED_KEY, value)
}
