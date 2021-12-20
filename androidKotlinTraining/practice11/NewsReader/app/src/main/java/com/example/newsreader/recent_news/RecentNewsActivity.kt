package com.example.newsreader.recent_news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.data.source.NewsItemsRepositoryFactory

class RecentNewsActivity : AppCompatActivity() {
  private lateinit var recentNewsPresenter: RecentNewsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)
    setSupportActionBar(findViewById(R.id.toolbar))
    supportActionBar?.apply {
      setDisplayShowHomeEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }

    val recentNewsFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as? RecentNewsFragment
        ?: RecentNewsFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    recentNewsPresenter =
      RecentNewsPresenter(
        recentNewsFragment,
        NewsItemsRepositoryFactory.getInstance(applicationContext)
      )
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
