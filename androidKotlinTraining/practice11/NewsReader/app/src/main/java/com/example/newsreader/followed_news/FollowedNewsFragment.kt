package com.example.newsreader.followed_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.NewsRecyclerViewAdapter
import com.example.newsreader.news_item.NewsItemFragmentFunctions
import com.example.newsreader.news_item.NewsItemFunctionsContract

class FollowedNewsFragment(
  private val newsItemFragmentFunctions: NewsItemFragmentFunctions = NewsItemFragmentFunctions()
): Fragment(), FollowedNewsContract.View, NewsItemFunctionsContract.View by newsItemFragmentFunctions
{
  companion object {
    fun newInstance() = FollowedNewsFragment()
  }

  override lateinit var presenter: FollowedNewsContract.Presenter
  private lateinit var newsRecyclerView: RecyclerView
  private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
  private lateinit var noFollowedItemsTextView: TextView

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
    val root = inflater.inflate(R.layout.fragment_followed_news, container, false)

    newsRecyclerView = root.findViewById(R.id.newsRecyclerView)
    recyclerViewAdapter = NewsRecyclerViewAdapter(
      newsItemClickListener = { newsItem -> presenter.onClickNewsItem(newsItem) },
      newsItemOptionsClickListener = { newsItem -> presenter.onClickNewsItemOptionsMenu(newsItem) }
    )
    newsRecyclerView.adapter = recyclerViewAdapter

    noFollowedItemsTextView = root.findViewById(R.id.noFollowedItemsTextView)

    newsItemFragmentFunctions.presenter = presenter
    newsItemFragmentFunctions.fragment = this

    return root
  }

  override fun showItemsInRecyclerView(newsItems: List<NewsItem>) {
    displayRecyclerView()
    recyclerViewAdapter.newsItems = newsItems
  }

  override fun showNoFollowedItems() {
    displayNoFollowedItemsTextView()
  }

  private fun displayRecyclerView() {
    newsRecyclerView.visibility = View.VISIBLE
    noFollowedItemsTextView.visibility = View.GONE
  }

  private fun displayNoFollowedItemsTextView() {
    newsRecyclerView.visibility = View.GONE
    noFollowedItemsTextView.visibility = View.VISIBLE
  }
}
