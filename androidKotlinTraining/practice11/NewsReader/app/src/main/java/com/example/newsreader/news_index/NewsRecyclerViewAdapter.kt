package com.example.newsreader.news_index

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem
import org.threeten.bp.ZonedDateTime

class NewsRecyclerViewAdapter(
  private val newsItemClickListener: (NewsItem) -> Unit,
  private val newsItemOptionsClickListener: (NewsItem) -> Unit
): RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {
  class ViewHolder private constructor(val newsItemView: View): RecyclerView.ViewHolder(newsItemView) {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newsItemView = layoutInflater.inflate(R.layout.news_recycler_view_item, parent, false)
        return ViewHolder(newsItemView)
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
    viewHolder.newsItemView.apply {
      findViewById<TextView>(R.id.headlineTextView).text = newsItem.title
      findViewById<TextView>(R.id.pubDateTextView).text = dateToTimeAgo(newsItem.pubDate)

      findViewById<ImageButton>(R.id.options_button)
        .setOnClickListener { newsItemOptionsClickListener(newsItem) }

      setOnClickListener { newsItemClickListener(newsItem) }
    }
  }

  private fun dateToTimeAgo(date: ZonedDateTime): String {
    return DateUtils.getRelativeTimeSpanString(date.toInstant().toEpochMilli()).toString()
  }
}
