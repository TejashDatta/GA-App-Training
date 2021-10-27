package com.example.newsreader.news_index

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem

class NewsIndexFragment: Fragment(), NewsIndexContract.View {
  companion object {
    fun newInstance() = NewsIndexFragment()
  }

  override lateinit var presenter: NewsIndexContract.Presenter
  private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onPause() {
    super.onPause()
    presenter.end()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.fragment_news_index, container, false)

    recyclerViewAdapter = NewsRecyclerViewAdapter { newsItem -> presenter.onClickNewsItem(newsItem) }
    root.findViewById<RecyclerView>(R.id.newsRecyclerView).adapter = recyclerViewAdapter

    swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
    swipeRefreshLayout.setOnRefreshListener { presenter.refreshNewsItems() }

    return root
  }

  override fun setRecyclerViewItems(newsItems: List<NewsItem>) {
    recyclerViewAdapter.newsItems = newsItems
    swipeRefreshLayout.isRefreshing = false
  }

  override fun openInTab(url: String) {
    CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(url))
  }
}
