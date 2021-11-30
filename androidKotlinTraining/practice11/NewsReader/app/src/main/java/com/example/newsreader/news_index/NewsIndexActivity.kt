package com.example.newsreader.news_index

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.data.source.NewsItemsRepositoryLocator
import com.example.newsreader.data.source.RecentNewsManager
import com.example.newsreader.data.source.SharedPreferencesRetriever
import com.example.newsreader.followed_news.FollowedNewsActivity

class NewsIndexActivity : AppCompatActivity() {
  private lateinit var newsIndexPresenter: NewsIndexPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)
    setSupportActionBar(findViewById(R.id.toolbar))

    val followedNewsSharedPreferences =
      SharedPreferencesRetriever(applicationContext).retrieveFollowedNews()
    val followedNewsManager = FollowedNewsManager(followedNewsSharedPreferences)

    val recentNewsSharedPreferences =
      SharedPreferencesRetriever(applicationContext).retrieveRecentNews()
    val recentNewsManager = RecentNewsManager(recentNewsSharedPreferences)

    val newsIndexFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as NewsIndexFragment?
        ?: NewsIndexFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    newsIndexPresenter =
      NewsIndexPresenter(
        newsIndexFragment,
        NewsItemsRepositoryLocator.repository,
        followedNewsManager,
        recentNewsManager,
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
}
