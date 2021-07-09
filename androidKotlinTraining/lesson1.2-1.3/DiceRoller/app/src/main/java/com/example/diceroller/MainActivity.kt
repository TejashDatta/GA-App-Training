package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes

enum class DiceImages(@DrawableRes val image: Int) {
  DICE_1(R.drawable.dice_1),
  DICE_2(R.drawable.dice_2),
  DICE_3(R.drawable.dice_3),
  DICE_4(R.drawable.dice_4),
  DICE_5(R.drawable.dice_5),
  DICE_6(R.drawable.dice_6)
}

class MainActivity : AppCompatActivity() {
  lateinit var diceImage1: ImageView
  lateinit var diceImage2: ImageView

  companion object {
    const val DICE_MAX_VALUE = 6
  }

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
    return when ((1..DICE_MAX_VALUE).random()) {
      1 -> DiceImages.DICE_1.image
      2 -> DiceImages.DICE_2.image
      3 -> DiceImages.DICE_3.image
      4 -> DiceImages.DICE_4.image
      5 -> DiceImages.DICE_5.image
      else -> DiceImages.DICE_6.image
    }
  }
}
