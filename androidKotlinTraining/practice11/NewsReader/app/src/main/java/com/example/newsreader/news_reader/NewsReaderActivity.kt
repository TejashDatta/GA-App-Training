package com.example.newsreader.news_reader

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.data.source.NewsItemsRepositoryFactory
import com.example.newsreader.followed_news.FollowedNewsFragment
import com.example.newsreader.followed_news.FollowedNewsPresenter
import com.example.newsreader.news_index.NewsIndexFragment
import com.example.newsreader.news_index.NewsIndexPresenter
import com.example.newsreader.recent_news.RecentNewsFragment
import com.example.newsreader.recent_news.RecentNewsPresenter
import com.google.android.material.navigation.NavigationView

class NewsReaderActivity : AppCompatActivity(), NewsReaderContract.View {
  override lateinit var presenter: NewsReaderContract.Presenter

  private lateinit var drawerLayout: DrawerLayout
  private lateinit var navigationView: NavigationView

  private var newsIndexFragment: NewsIndexFragment? = null
  private var newsIndexPresenter: NewsIndexPresenter? = null

  private var followedNewsFragment: FollowedNewsFragment? = null
  private var followedNewsPresenter: FollowedNewsPresenter? = null

  private var recentNewsFragment: RecentNewsFragment? = null
  private var recentNewsPresenter: RecentNewsPresenter? = null

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_news_reader)

    setSupportActionBar(findViewById(R.id.toolbar))
    supportActionBar?.apply {
      setDisplayShowHomeEnabled(true)
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    drawerLayout = findViewById(R.id.drawer_layout)
    navigationView = findViewById(R.id.navigation_view)
    setupDrawerContent()

    presenter = NewsReaderPresenter(this)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      drawerLayout.openDrawer(GravityCompat.START)
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showAllNews() {
    if(supportFragmentManager.findFragmentById(R.id.contentFrame) as? NewsIndexFragment != null)  return

    val currentFragment = newsIndexFragment ?: NewsIndexFragment.newInstance()
    newsIndexFragment = currentFragment
    replaceFragmentInActivity(R.id.contentFrame, currentFragment)

    if (newsIndexPresenter == null) {
      newsIndexPresenter = NewsIndexPresenter(
        currentFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext),
        SchedulerProvider()
      )
    }
  }

  override fun showGoogleNews() {
    TODO("Not yet implemented")
  }

  override fun showToyokeizaiNews() {
    TODO("Not yet implemented")
  }

  override fun showFollowedNews() {
    if(supportFragmentManager.findFragmentById(R.id.contentFrame) as? FollowedNewsFragment != null)  return

    val currentFragment = followedNewsFragment ?: FollowedNewsFragment.newInstance()
    followedNewsFragment = currentFragment
    replaceFragmentInActivity(R.id.contentFrame, currentFragment)

    if (followedNewsPresenter == null) {
      followedNewsPresenter = FollowedNewsPresenter(
        currentFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext),
        SchedulerProvider()
      )
    }
  }

  override fun showRecentNews() {
    if(supportFragmentManager.findFragmentById(R.id.contentFrame) as? RecentNewsFragment != null)  return

    val currentFragment = recentNewsFragment ?: RecentNewsFragment.newInstance()
    recentNewsFragment = currentFragment
    replaceFragmentInActivity(R.id.contentFrame, currentFragment)

    if (recentNewsPresenter == null) {
      recentNewsPresenter = RecentNewsPresenter(
        currentFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext)
      )
    }
  }

  private fun setupDrawerContent() {
    navigationView.setNavigationItemSelectedListener { menuItem ->
      uncheckMenuItems()

      when(menuItem.itemId) {
        R.id.action_all_news -> presenter.onClickAllNews()
        R.id.action_followed_news -> presenter.onClickFollowedNews()
        R.id.action_recent_news -> presenter.onClickRecentNews()
      }

      menuItem.isChecked = true
      drawerLayout.closeDrawers()
      true
    }
  }

  private fun uncheckMenuItems() {
    navigationView.menu.forEach { it.isChecked = false }
  }
}
