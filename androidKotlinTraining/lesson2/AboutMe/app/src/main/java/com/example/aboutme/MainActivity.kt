package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
  private lateinit var editText: EditText
  private lateinit var inputMethodManager: InputMethodManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    editText = findViewById(R.id.nickname_edit)
    inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    findViewById<Button>(R.id.done_button).setOnClickListener {
      addNickname(it)
    }
    findViewById<TextView>(R.id.nickname_text).setOnClickListener {
      updateNickname(it)
    }
  }

  private fun addNickname(view: View) {
    val nicknameTextView = findViewById<TextView>(R.id.nickname_text)

    nicknameTextView.text = editText.text

    nicknameTextView.visibility = View.VISIBLE
    editText.visibility = View.GONE
    view.visibility = View.GONE

    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
  }

  private fun updateNickname (view:View) {
    val doneButton = findViewById<Button>(R.id.done_button)

    editText.visibility = View.VISIBLE
    doneButton.visibility = View.VISIBLE
    view.visibility = View.GONE

    editText.requestFocus()
    inputMethodManager.showSoftInput(editText, 0)
  }
}
