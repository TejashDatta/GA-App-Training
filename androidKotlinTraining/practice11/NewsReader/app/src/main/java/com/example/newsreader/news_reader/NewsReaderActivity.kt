package com.example.newsreader.news_reader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.add_news_source.AddNewsSourceActivity
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.NewsRepositoryFactory
import com.example.newsreader.followed_news.FollowedNewsFragment
import com.example.newsreader.followed_news.FollowedNewsPresenter
import com.example.newsreader.news_index.NewsIndexFragment
import com.example.newsreader.news_index.NewsIndexPresenter
import com.example.newsreader.recent_news.RecentNewsFragment
import com.example.newsreader.recent_news.RecentNewsPresenter
import com.google.android.material.navigation.NavigationView

class NewsReaderActivity : AppCompatActivity(), NewsReaderContract.View {
  companion object {
    private const val NEWS_INDEX_ALL_FRAGMENT_TAG = "NEWS_INDEX_ALL_FRAGMENT"
    private const val NEWS_INDEX_GOOGLE_FRAGMENT_TAG = "NEWS_INDEX_GOOGLE_FRAGMENT"
    private const val NEWS_INDEX_TOYOKEIZAI_FRAGMENT_TAG = "NEWS_INDEX_TOYOKEIZAI_FRAGMENT"
    private const val FOLLOWED_NEWS_FRAGMENT_TAG = "FOLLOWED_NEWS_FRAGMENT"
    private const val RECENT_NEWS_FRAGMENT_TAG = "RECENT_NEWS_FRAGMENT"
  }

  override lateinit var presenter: NewsReaderContract.Presenter

  lateinit var newsRepository: NewsRepository

  private lateinit var drawerLayout: DrawerLayout
  private lateinit var navigationViewMain: NavigationView
  private lateinit var navigationViewFooter: NavigationView

  @IdRes private val contentFrameId = R.id.contentFrame

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

    newsRepository = NewsRepositoryFactory.getInstance(applicationContext)

    drawerLayout = findViewById(R.id.drawer_layout)
    navigationViewMain = findViewById(R.id.navigation_view_main)
    navigationViewFooter = findViewById(R.id.navigation_view_footer)
    setupDrawerFooterContent()

    presenter = NewsReaderPresenter(this, newsRepository)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      drawerLayout.openDrawer(GravityCompat.START)
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showAllNews() {
    val cachedFragment = findNewsIndexFragmentByTag(NEWS_INDEX_ALL_FRAGMENT_TAG)

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: NewsIndexFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      NewsIndexPresenter(
        NewsIndexPresenter.NewsSource.ALL,
        nextFragment,
        newsRepository,
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, NEWS_INDEX_ALL_FRAGMENT_TAG, isNewInstance)
  }

  override fun showGoogleNews() {
    val cachedFragment = findNewsIndexFragmentByTag(NEWS_INDEX_GOOGLE_FRAGMENT_TAG)

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: NewsIndexFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      NewsIndexPresenter(
        NewsIndexPresenter.NewsSource.GOOGLE,
        nextFragment,
        newsRepository,
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, NEWS_INDEX_GOOGLE_FRAGMENT_TAG, isNewInstance)
  }

  override fun showToyokeizaiNews() {
    val cachedFragment = findNewsIndexFragmentByTag(NEWS_INDEX_TOYOKEIZAI_FRAGMENT_TAG)

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: NewsIndexFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      NewsIndexPresenter(
        NewsIndexPresenter.NewsSource.TOYOKEIZAI,
        nextFragment,
        newsRepository,
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, NEWS_INDEX_TOYOKEIZAI_FRAGMENT_TAG, isNewInstance)
  }

  override fun showFollowedNews() {
    val cachedFragment =
      supportFragmentManager.findFragmentByTag(FOLLOWED_NEWS_FRAGMENT_TAG) as? FollowedNewsFragment

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: FollowedNewsFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      FollowedNewsPresenter(
        nextFragment,
        newsRepository,
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, FOLLOWED_NEWS_FRAGMENT_TAG, isNewInstance)
  }

  override fun showRecentNews() {
    val cachedFragment =
      supportFragmentManager.findFragmentByTag(RECENT_NEWS_FRAGMENT_TAG) as? RecentNewsFragment

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: RecentNewsFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      RecentNewsPresenter(
        nextFragment,
        newsRepository
      )
    }

    changeFragment(nextFragment, RECENT_NEWS_FRAGMENT_TAG, isNewInstance)
  }

  override fun showGeneralNews(newsSource: NewsSource) {
    // TODO: enable news index fragment to use general news    
    Log.d("NewsReaderActivity", newsSource.name)
  }

  override fun showAddNewsSource() {
    startActivity(
      Intent(applicationContext, AddNewsSourceActivity::class.java)
    )
  }
  
  private fun findNewsIndexFragmentByTag(tag: String): NewsIndexFragment? {
    return supportFragmentManager.findFragmentByTag(tag) as? NewsIndexFragment
  }

  private fun currentFragment() = supportFragmentManager.findFragmentById(contentFrameId)

  private fun changeFragment(nextFragment: Fragment, tag: String, isNewInstance: Boolean) {
    supportFragmentManager.beginTransaction().apply {
      currentFragment()?.let { detach(it) }

      if (isNewInstance) {
        add(contentFrameId, nextFragment, tag)
      } else {
        attach(nextFragment)
      }
    }.commit()
  }

  override fun setupDrawerMainContent(newsSources: List<NewsSource>) {
    addSourcesToDrawerMain(newsSources)
    setDrawerMainItemListeners()
  }

  private fun addSourcesToDrawerMain(newsSources: List<NewsSource>) {
    val menu = navigationViewMain.menu
    val startIndex = menu.findItem(R.id.action_toyokeizai_news).order + 1
    newsSources.forEachIndexed { index, newsSource ->
      menu.removeItem(index)
      menu.add(R.id.drawer_main_group, index, startIndex + index, newsSource.name)
    }
  }

  private fun setDrawerMainItemListeners() {
    navigationViewMain.setNavigationItemSelectedListener { menuItem ->
      when(menuItem.itemId) {
        R.id.action_all_news -> presenter.onClickAllNews()
        R.id.action_google_news -> presenter.onClickGoogleNews()
        R.id.action_toyokeizai_news -> presenter.onClickToyokeizaiNews()
        R.id.action_followed_news -> presenter.onClickFollowedNews()
        R.id.action_recent_news -> presenter.onClickRecentNews()
        else -> presenter.onClickGeneralNews(menuItem.title as String)
      }

      menuItem.isChecked = true
      drawerLayout.closeDrawers()
      true
    }
  }

  private fun setupDrawerFooterContent() {
    setDrawerFooterItemListeners()
  }

  private fun setDrawerFooterItemListeners() {
    navigationViewFooter.setNavigationItemSelectedListener { menuItem ->
      when(menuItem.itemId) {
        R.id.action_add_news_source -> presenter.onClickAddNewsSource()
      }
      true
    }
  }
}
