package com.example.newsreader.news_reader

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.forEach
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
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

  @IdRes private val contentFrameId = R.id.contentFrame

  private var newsIndexFragment: NewsIndexFragment? = null

  private var followedNewsFragment: FollowedNewsFragment? = null

  private var recentNewsFragment: RecentNewsFragment? = null

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
    if (currentFragment() is NewsIndexFragment) return

    val nextFragment = newsIndexFragment ?: NewsIndexFragment.newInstance()
    val isNewInstance = newsIndexFragment == null
    newsIndexFragment = nextFragment

    if (isNewInstance) {
      NewsIndexPresenter(
        nextFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext),
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, isNewInstance)
  }

  override fun showGoogleNews() {
    TODO("Not yet implemented")
  }

  override fun showToyokeizaiNews() {
    TODO("Not yet implemented")
  }

  override fun showFollowedNews() {
    if (currentFragment() is FollowedNewsFragment) return

    val nextFragment = followedNewsFragment ?: FollowedNewsFragment.newInstance()
    val isNewInstance = followedNewsFragment == null
    followedNewsFragment = nextFragment

    if (isNewInstance) {
      FollowedNewsPresenter(
        nextFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext),
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, isNewInstance)
  }

  override fun showRecentNews() {
    if (currentFragment() is RecentNewsFragment) return

    val nextFragment = recentNewsFragment ?: RecentNewsFragment.newInstance()
    val isNewInstance = recentNewsFragment == null
    recentNewsFragment = nextFragment

    if (isNewInstance) {
      RecentNewsPresenter(
        nextFragment,
        NewsItemsRepositoryFactory.getOrCreateRepository(applicationContext)
      )
    }

    changeFragment(nextFragment, isNewInstance)
  }

  private fun currentFragment() = supportFragmentManager.findFragmentById(contentFrameId)

  private fun changeFragment(nextFragment: Fragment, isNewInstance: Boolean) {
    supportFragmentManager.beginTransaction().apply {
      currentFragment()?.let { detach(it) }

      if (isNewInstance) {
        add(contentFrameId, nextFragment)
      } else {
        attach(nextFragment)
      }
    }.commit()
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
