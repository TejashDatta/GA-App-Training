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
      ColorButtons.values().map { findViewById<Button>(it.id) } +
      findViewById<View>(R.id.constraint_layout)

    for (clickableView in clickableViews) {
      clickableView.setOnClickListener { setBackground(it) }
    }
  }

  private fun setBackground(view: View) {
    when(view.id) {
      R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
      R.id.box_two_text -> view.setBackgroundResource(R.drawable.sky)
      R.id.box_three_text -> view.setBackgroundColor(Color.BLUE)
      R.id.box_four_text -> view.setBackgroundColor(Color.MAGENTA)
      R.id.box_five_text -> view.setBackgroundColor(Color.BLUE)
      R.id.red_button ->
        findViewById<TextView>(BoxTexts.BOX_THREE_TEXT.id).setBackgroundResource(R.color.my_red)
      R.id.yellow_button ->
        findViewById<TextView>(BoxTexts.BOX_FOUR_TEXT.id).setBackgroundResource(R.color.my_yellow)
      R.id.green_button ->
        findViewById<TextView>(BoxTexts.BOX_FIVE_TEXT.id).setBackgroundResource(R.color.my_green)

      else -> view.setBackgroundColor(Color.LTGRAY)
    }
  }
}
