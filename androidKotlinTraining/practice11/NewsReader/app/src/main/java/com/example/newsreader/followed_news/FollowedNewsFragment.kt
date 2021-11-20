package com.example.newsreader.followed_news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.NewsRecyclerViewAdapter
import com.example.newsreader.news_index.OptionsModalBottomSheet

class FollowedNewsFragment: Fragment(), FollowedNewsContract.View {
  companion object {
    fun newInstance() = FollowedNewsFragment()
  }

  override lateinit var presenter: FollowedNewsContract.Presenter
  private lateinit var newsRecyclerView: RecyclerView
  private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter
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
    val root = inflater.inflate(R.layout.fragment_followed_news, container, false)

    newsRecyclerView = root.findViewById(R.id.newsRecyclerView)
    recyclerViewAdapter = NewsRecyclerViewAdapter(
      newsItemClickListener = { newsItem -> presenter.onClickNewsItem(newsItem) },
      newsItemOptionsClickListener = { newsItem -> presenter.onClickNewsItemOptionsMenu(newsItem) }
    )
    newsRecyclerView.adapter = recyclerViewAdapter

    messageTextView = root.findViewById(R.id.messageTextView)

    return root
  }

  override fun showItemsInRecyclerView(newsItems: List<NewsItem>) {
    displayRecyclerView()
    recyclerViewAdapter.newsItems = newsItems
  }

  override fun openInTab(url: String) {
    CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(url))
  }

  override fun openOptionsMenu(newsItem: NewsItem, isNewsItemSaved: Boolean) {
    OptionsModalBottomSheet(isNewsItemSaved) { option -> presenter.onClickNewsItemOption(newsItem, option) }
      .show(childFragmentManager, tag)
  }

  override fun shareNews(newsItem: NewsItem) {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, newsItem.link)
      putExtra(Intent.EXTRA_TITLE, newsItem.title)
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }

  override fun showToastForSaveClicked(isSaved: Boolean) {
    val messageResourceID = if (isSaved) R.string.item_followed else R.string.item_unfollowed

    Toast.makeText(context, messageResourceID, Toast.LENGTH_SHORT).show()
  }

  override fun showNoResults() {
    displayMessageTextView()
    messageTextView.text = getString(R.string.no_followed_items)
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
