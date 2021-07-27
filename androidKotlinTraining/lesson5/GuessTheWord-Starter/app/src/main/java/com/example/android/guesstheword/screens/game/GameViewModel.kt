package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
  companion object {
    private const val DONE = 0L

    private const val ONE_SECOND = 1000L

    private const val COUNTDOWN_TIME = 60000L
  }

  private lateinit var wordList: MutableList<String>

  private val _word = MutableLiveData<String>()
  val word: LiveData<String>
    get() = _word

  val wordHint: LiveData<String> = Transformations.map(word) { word ->
    val randomPosition = (1..word.length).random()
    "Current word has ${word.length} letters" +
      "\nThe letter at position $randomPosition is ${word.get(randomPosition - 1).toUpperCase()}"
  }

  private val _score = MutableLiveData<Int>()
  val score: LiveData<Int>
    get() = _score

  private val _eventGameFinish = MutableLiveData<Boolean>()
  val eventGameFinish: LiveData<Boolean>
    get() = _eventGameFinish

  private val _currentTime = MutableLiveData<Long>()

  val currentTimeString: LiveData<String> = Transformations.map(_currentTime) { time ->
    DateUtils.formatElapsedTime(time)
  }

  private val timer: CountDownTimer

  init {
    _word.value = ""
    _score.value = 0
    resetList()
    nextWord()

    timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
      override fun onTick(millisUntilFinished: Long) {
        _currentTime.value = millisUntilFinished/ONE_SECOND
      }

      override fun onFinish() {
        _currentTime.value = DONE
        onGameFinish()
      }
    }

    timer.start()
  }

  override fun onCleared() {
    super.onCleared()
    timer.cancel()
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
      resetList()
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
    _score.value = score.value?.minus(1)
    nextWord()
  }

  fun onCorrect() {
    _score.value = score.value?.plus(1)
    nextWord()
  }
}
