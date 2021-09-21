package com.example.calculator.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.util.replaceFragmentInActivity

class HistoryActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.history_act)

    val historyFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as HistoryFragment?
        ?: HistoryFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }
  }
}
