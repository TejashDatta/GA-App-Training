package com.example.newsreader.news_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_item.NewsItemFragmentFunctions
import com.example.newsreader.news_item.NewsItemFunctionsContract

class NewsIndexFragment(
  private val newsItemFragmentFunctions: NewsItemFragmentFunctions = NewsItemFragmentFunctions()
):
  Fragment(), NewsIndexContract.View, NewsItemFunctionsContract.View by newsItemFragmentFunctions
{
  companion object {
    fun newInstance() = NewsIndexFragment()
  }

  override lateinit var presenter: NewsIndexContract.Presenter
  private lateinit var newsRecyclerView: RecyclerView
  private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var messageTextView: TextView

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

    newsRecyclerView = root.findViewById(R.id.newsRecyclerView)
    recyclerViewAdapter = NewsRecyclerViewAdapter(
      newsItemClickListener = { newsItem -> presenter.onClickNewsItem(newsItem) },
      newsItemOptionsClickListener = { newsItem -> presenter.onClickNewsItemOptionsMenu(newsItem) }
    )
    newsRecyclerView.adapter = recyclerViewAdapter

    messageTextView = root.findViewById(R.id.messageTextView)

    swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
    swipeRefreshLayout.setOnRefreshListener { presenter.refreshNewsItems() }

    newsItemFragmentFunctions.presenter = presenter
    newsItemFragmentFunctions.fragment = this

    return root
  }

  override fun showItemsInRecyclerView(newsItems: List<NewsItem>) {
    displayRecyclerView()
    recyclerViewAdapter.newsItems = newsItems
    swipeRefreshLayout.isRefreshing = false
  }

  override fun showLoading() {
    displayMessageTextView()
    messageTextView.text = getString(R.string.loading)
  }

  override fun showError() {
    displayMessageTextView()
    messageTextView.text = getString(R.string.error)
  }

  override fun showNoResults() {
    displayMessageTextView()
    messageTextView.text = getString(R.string.no_results)
  }
  
  private fun displayRecyclerView() {
    newsRecyclerView.visibility = View.VISIBLE
    messageTextView.visibility = View.GONE
  }

  private fun displayMessageTextView() {
    newsRecyclerView.visibility = View.GONE
    messageTextView.visibility = View.VISIBLE
  }
}
