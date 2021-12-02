package com.example.newsreader.followed_news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.data.source.FollowedNewsSharedPreferencesRetriever

class FollowedNewsActivity : AppCompatActivity() {
  private lateinit var followedNewsPresenter: FollowedNewsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)
    setSupportActionBar(findViewById(R.id.toolbar))
    supportActionBar?.apply {
      setDisplayShowHomeEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }

    val followedNewsSharedPreferences =
      FollowedNewsSharedPreferencesRetriever(applicationContext).retrieve()
    val followedNewsManager = FollowedNewsManager(followedNewsSharedPreferences)

    val followedNewsFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as FollowedNewsFragment?
        ?: FollowedNewsFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    followedNewsPresenter =
      FollowedNewsPresenter(
        followedNewsFragment,
        followedNewsManager,
        SchedulerProvider()
      )
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
