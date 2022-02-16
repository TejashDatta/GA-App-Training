package com.example.newsreader.news_reader

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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

  override fun showNews(newsSourceName: String) {
    val fragmentTag = "${newsSourceName}_fragment"
    val cachedFragment = findNewsIndexFragmentByTag(fragmentTag)

    if(cachedFragment?.isVisible == true) return

    val nextFragment = cachedFragment ?: NewsIndexFragment.newInstance()
    val isNewInstance = cachedFragment == null

    if (isNewInstance) {
      NewsIndexPresenter(
        newsSourceName,
        nextFragment,
        newsRepository,
        SchedulerProvider()
      )
    }

    changeFragment(nextFragment, fragmentTag, isNewInstance)
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
    deleteAllItemsInDrawerMain()
    addSourcesToDrawerMain(newsSources)
    setupStaticItemsInDrawerMain()
    makeSingleItemCheckableInDrawerMain()
    setDrawerMainItemListeners()
  }

  private fun deleteAllItemsInDrawerMain() {
    navigationViewMain.menu.removeGroup(R.id.drawer_main_group)
  }

  private fun setupStaticItemsInDrawerMain() {
    val menu = navigationViewMain.menu
    val startIndex = menu.size()
    menu.add(R.id.drawer_main_group, R.id.action_followed_news, startIndex, getString(R.string.drawer_followed_news))
    menu.add(R.id.drawer_main_group, R.id.action_recent_news, startIndex + 1, getString(R.string.drawer_recent_news))
  }

  private fun addSourcesToDrawerMain(newsSources: List<NewsSource>) {
    val menu = navigationViewMain.menu
    newsSources.forEachIndexed { index, newsSource ->
      menu.add(R.id.drawer_main_group, Menu.NONE, index, newsSource.name)
    }
  }

  private fun makeSingleItemCheckableInDrawerMain() {
    val menu = navigationViewMain.menu
    menu.setGroupCheckable(R.id.drawer_main_group, true, true)
    menu.getItem(0).isChecked = true
  }

  private fun setDrawerMainItemListeners() {
    navigationViewMain.setNavigationItemSelectedListener { menuItem ->
      when(menuItem.itemId) {
        R.id.action_followed_news -> presenter.onClickFollowedNews()
        R.id.action_recent_news -> presenter.onClickRecentNews()
        else -> presenter.onClickNewsSource(menuItem.title as String)
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
