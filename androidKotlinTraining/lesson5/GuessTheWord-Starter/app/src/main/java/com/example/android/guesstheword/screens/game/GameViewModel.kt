package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

  var word = ""

  var score = 0

  private lateinit var wordList: MutableList<String>

  init {
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
      "basketball",
      "cat",
      "change",
      "snail",
      "soup",
      "calendar",
      "sad",
      "desk",
      "guitar",
      "home",
      "railway",
      "zebra",
      "jelly",
      "car",
      "crow",
      "trade",
      "bag",
      "roll",
      "bubble"
    )
    wordList.shuffle()
  }

  private fun nextWord() {
    if (!wordList.isEmpty()) {
      //Select and remove a word from the list
      word = wordList.removeAt(0)
    }
  }

  fun onSkip() {
    score--
    nextWord()
  }

  fun onCorrect() {
    score++
    nextWord()
  }
}
