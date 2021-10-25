package com.example.newsreader.news_index

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.NewsItemsRepository
import com.example.newsreader.data.models.NewsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsIndexFragment: Fragment(), NewsIndexContract.View {
  companion object {
    fun newInstance() = NewsIndexFragment()
  }

  override lateinit var presenter: NewsIndexContract.Presenter
  private lateinit var recyclerViewAdapter: NewsRecyclerViewAdapter

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

    recyclerViewAdapter = NewsRecyclerViewAdapter()
    root.findViewById<RecyclerView>(R.id.newsRecyclerView).adapter = recyclerViewAdapter

    return root
  }

  override fun setRecyclerViewItems(newsItems: List<NewsItem>) {
    recyclerViewAdapter.newsItems = newsItems
  }
}
