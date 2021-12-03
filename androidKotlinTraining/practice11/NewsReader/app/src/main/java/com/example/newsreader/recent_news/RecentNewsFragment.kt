package com.example.newsreader.recent_news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem

class RecentNewsFragment: Fragment(), RecentNewsContract.View {
  companion object {
    fun newInstance() = RecentNewsFragment()
  }

  override lateinit var presenter: RecentNewsContract.Presenter
  private lateinit var recentNewsRecyclerView: RecyclerView
  private lateinit var recyclerViewAdapter: RecentNewsRecyclerViewAdapter
  private lateinit var noRecentItemsTextView: TextView

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.fragment_recent_news, container, false)

    recentNewsRecyclerView = root.findViewById(R.id.recentNewsRecyclerView)
    recyclerViewAdapter = RecentNewsRecyclerViewAdapter(
      newsItemClickListener = { newsItem -> presenter.onClickNewsItem(newsItem) }
    )
    recentNewsRecyclerView.adapter = recyclerViewAdapter

    noRecentItemsTextView = root.findViewById(R.id.noRecentItemsTextView)

    return root
  }

  override fun openInCustomTab(url: String) {
    CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(url))
  }

  override fun showItemsInRecyclerView(newsItems: List<NewsItem>) {
    displayRecyclerView()
    recyclerViewAdapter.newsItems = newsItems
  }

  override fun showNoRecentItems() {
    displayNoFollowedItemsTextView()
  }

  private fun displayRecyclerView() {
    recentNewsRecyclerView.visibility = View.VISIBLE
    noRecentItemsTextView.visibility = View.GONE
  }

  private fun displayNoFollowedItemsTextView() {
    recentNewsRecyclerView.visibility = View.GONE
    noRecentItemsTextView.visibility = View.VISIBLE
  }
}
