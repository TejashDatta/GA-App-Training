package com.example.newsreader.news_index

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.data.source.NewsItemsRepositoryFactory
import com.example.newsreader.followed_news.FollowedNewsActivity
import com.example.newsreader.recent_news.RecentNewsActivity

class NewsIndexActivity : AppCompatActivity() {
  private lateinit var newsIndexPresenter: NewsIndexPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)
    setSupportActionBar(findViewById(R.id.toolbar))

    val newsIndexFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as NewsIndexFragment?
        ?: NewsIndexFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    newsIndexPresenter =
      NewsIndexPresenter(
        newsIndexFragment,
        NewsItemsRepositoryFactory.getInstance(applicationContext),
        SchedulerProvider()
      )
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.toolbar_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_share -> {
        shareGoogleNewsHomepageURL()
        true
      }
      R.id.action_followed_items -> {
        showSavedNews()
        true
      }
      R.id.action_recent_items -> {
        showRecentNews()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun shareGoogleNewsHomepageURL() {
    val googleNewsHomepageURL = "https://news.google.com/topstories?hl=ja&gl=JP&ceid=JP:ja"

    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, googleNewsHomepageURL)
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }

  private fun showSavedNews() {
    val intent = Intent(applicationContext, FollowedNewsActivity::class.java)
    startActivity(intent)
  }

  private fun showRecentNews() {
    val intent = Intent(applicationContext, RecentNewsActivity::class.java)
    startActivity(intent)
  }
}
