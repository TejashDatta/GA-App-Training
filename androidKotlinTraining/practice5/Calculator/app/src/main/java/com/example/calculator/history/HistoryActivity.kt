package com.example.calculator.history

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.util.replaceFragmentInActivity

class HistoryActivity : AppCompatActivity() {
  private lateinit var historyPresenter: HistoryPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.app_act)

    setSupportActionBar(findViewById(R.id.toolbar))
    supportActionBar?.apply {
      setDisplayShowHomeEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }

    val historyManager = HistoryManager(
      getSharedPreferences(getString(R.string.history_preference_file_key), Context.MODE_PRIVATE))

    val historyFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as HistoryFragment?
        ?: HistoryFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    historyPresenter = HistoryPresenter(historyFragment, historyManager)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
