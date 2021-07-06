package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  var resultText: String
    get() = findViewById<TextView>(R.id.result_text).text.toString()
    set(value) {
      findViewById<TextView>(R.id.result_text).text = value
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val rollButton: Button = findViewById(R.id.roll_button)
    rollButton.setOnClickListener { rollDice() }

    val countUpButton: Button = findViewById(R.id.count_up_button)
    countUpButton.setOnClickListener { countUp() }
  }

  private fun rollDice() {
    val randomInt = (1..6).random()
    resultText = randomInt.toString()

//    Toast.makeText(this, "button clicked",
//      Toast.LENGTH_SHORT).show()
  }

  private fun countUp() {
    val resultNumber = resultText.toIntOrNull()

    resultText = when {
      resultNumber == null -> 1
      resultNumber < 6 -> resultNumber + 1
      else -> 6
    }.toString()
  }
