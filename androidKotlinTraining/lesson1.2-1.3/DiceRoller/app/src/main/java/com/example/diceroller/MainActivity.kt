package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes

class MainActivity : AppCompatActivity() {
  lateinit var diceImage1: ImageView
  lateinit var diceImage2: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    diceImage1 = findViewById(R.id.dice_image_1)
    diceImage2 = findViewById(R.id.dice_image_2)

    val rollButton: Button = findViewById(R.id.roll_button)
    rollButton.setOnClickListener { rollDice() }
  }

  private fun rollDice() {
    diceImage1.setImageResource(getRandomDiceImage())
    diceImage2.setImageResource(getRandomDiceImage())
  }

  @DrawableRes
  private fun getRandomDiceImage(): Int {
    return when ((1..6).random()) {
      1 -> R.drawable.dice_1
      2 -> R.drawable.dice_2
      3 -> R.drawable.dice_3
      4 -> R.drawable.dice_4
      5 -> R.drawable.dice_5
      else -> R.drawable.dice_6
    }
  }
}
