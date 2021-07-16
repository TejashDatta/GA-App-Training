package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes

enum class BoxTexts(@IdRes val id: Int) {
  BOX_ONE_TEXT(R.id.box_one_text),
  BOX_TWO_TEXT(R.id.box_two_text),
  BOX_THREE_TEXT(R.id.box_three_text),
  BOX_FOUR_TEXT(R.id.box_four_text),
  BOX_FIVE_TEXT(R.id.box_five_text)
}

enum class ColorButtons(@IdRes val id: Int) {
  RED_BUTTON(R.id.red_button),
  YELLOW_BUTTON(R.id.yellow_button),
  GREEN_BUTTON(R.id.green_button)
}

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setListeners()
  }

  private fun setListeners() {
    val clickableViews: List<View> =
      BoxTexts.values().map { findViewById<TextView>(it.id) } +
      findViewById<View>(R.id.constraint_layout)
    val buttons = ColorButtons.values().map { findViewById<Button>(it.id) }

    for (clickableView in clickableViews) {
      clickableView.setOnClickListener { setBackground(it) }
    }
    for (button in buttons) {
      button.setOnClickListener { setBackgroundFromButton(it as Button) }
    }
  }

  private fun setBackground(view: View) {
    when(view.id) {
      BoxTexts.BOX_ONE_TEXT.id -> view.setBackgroundColor(Color.DKGRAY)
      BoxTexts.BOX_TWO_TEXT.id -> view.setBackgroundResource(R.drawable.sky)
      BoxTexts.BOX_THREE_TEXT.id -> view.setBackgroundColor(Color.BLUE)
      BoxTexts.BOX_FOUR_TEXT.id -> view.setBackgroundColor(Color.MAGENTA)
      BoxTexts.BOX_FIVE_TEXT.id -> view.setBackgroundColor(Color.BLUE)
      else -> view.setBackgroundColor(Color.LTGRAY)
    }
  }

  private fun setBackgroundFromButton(button: Button) {
    when(button.id) {
      ColorButtons.RED_BUTTON.id ->
        findViewById<TextView>(BoxTexts.BOX_THREE_TEXT.id).setBackgroundResource(R.color.my_red)
      ColorButtons.YELLOW_BUTTON.id ->
        findViewById<TextView>(BoxTexts.BOX_FOUR_TEXT.id).setBackgroundResource(R.color.my_yellow)
      ColorButtons.GREEN_BUTTON.id ->
        findViewById<TextView>(BoxTexts.BOX_FIVE_TEXT.id).setBackgroundResource(R.color.my_green)
    }
  }
}
