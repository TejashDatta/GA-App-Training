package com.example.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.example.aboutme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var inputMethodManager: InputMethodManager
  private val myName = MyName("Tejash Datta")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.myName = myName

    inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    binding.doneButton.setOnClickListener {
      addNickname()
    }
    binding.nicknameText.setOnClickListener {
      updateNickname()
    }
  }

  private fun addNickname() {
    binding.apply {
      myName?.nickname = nicknameEdit.text.toString()
      invalidateAll()

      nicknameText.visibility = View.VISIBLE
      nicknameEdit.visibility = View.GONE
      doneButton.visibility = View.GONE

      inputMethodManager.hideSoftInputFromWindow(doneButton.windowToken, 0)
    }
  }

  private fun updateNickname () {
    binding.apply {
      nicknameEdit.visibility = View.VISIBLE
      doneButton.visibility = View.VISIBLE
      nicknameText.visibility = View.GONE

      nicknameEdit.requestFocus()
      inputMethodManager.showSoftInput(nicknameEdit, 0)
    }
  }
}
