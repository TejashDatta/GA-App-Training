package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
  private lateinit var wordList: MutableList<String>

  private val _word = MutableLiveData<String>()
  val word: LiveData<String>
    get() = _word

  private val _score = MutableLiveData<Int>()
  val score: LiveData<Int>
    get() = _score

  private val _eventGameFinish = MutableLiveData<Boolean>()
  val eventGameFinish: LiveData<Boolean>
    get() = _eventGameFinish

  init {
    _word.value = ""
    _score.value = 0
    resetList()
    nextWord()
  }

  override fun onCleared() {
    super.onCleared()
    Log.i("GameViewModel", "GameViewModel destroyed")
  }

  private fun resetList() {
    wordList = mutableListOf(
      "queen",
      "hospital",
      "basketball"
    )
    wordList.shuffle()
  }

  private fun nextWord() {
    if (wordList.isEmpty()) {
      onGameFinish()
    } else {
      _word.value = wordList.removeAt(0)
    }
  }

  fun onGameFinish() {
    _eventGameFinish.value = true
  }

  fun onGameFinishComplete() {
    _eventGameFinish.value = false
  }

  fun onSkip() {
    score.value?.let { _score.setValue(it.dec()) }
    nextWord()
  }

  fun onCorrect() {
    score.value?.let { _score.setValue(it.inc()) }
    nextWord()
  }
}
