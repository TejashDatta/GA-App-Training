package com.example.newsreader.recent_news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem

class RecentNewsRecyclerViewAdapter(
  private val newsItemClickListener: (NewsItem) -> Unit
): RecyclerView.Adapter<RecentNewsRecyclerViewAdapter.ViewHolder>() {
  class ViewHolder private constructor(
    val recentNewsItemView: View): RecyclerView.ViewHolder(recentNewsItemView) {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recentNewsItemView =
          layoutInflater.inflate(R.layout.recent_news_recycler_view_item, parent, false)
        return ViewHolder(recentNewsItemView)
      }
    }
  }

  var newsItems: List<NewsItem> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = newsItems.size

  override fun onCreateViewHolder(parent: ViewGroup, _viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    val newsItem = newsItems[position]
    viewHolder.recentNewsItemView.apply {
      findViewById<TextView>(R.id.sourceTextView).text = newsItem.source
      findViewById<TextView>(R.id.headlineTextView).text = newsItem.title

      setOnClickListener { newsItemClickListener(newsItem) }
    }
  }
}
