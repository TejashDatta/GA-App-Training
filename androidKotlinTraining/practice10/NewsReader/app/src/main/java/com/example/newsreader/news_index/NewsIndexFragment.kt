package com.example.newsreader.news_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.R
import com.example.newsreader.data.NewsItemsRepository

class NewsIndexFragment: Fragment(), NewsIndexContract.View {
  companion object {
    fun newInstance() = NewsIndexFragment()
  }

  override lateinit var presenter: NewsIndexContract.Presenter
  private lateinit var newsRecyclerView: RecyclerView

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    val root = inflater.inflate(R.layout.fragment_news_index, container, false)

    newsRecyclerView = root.findViewById(R.id.newsRecyclerView)

    val adapter = NewsRecyclerViewAdapter()

    newsRecyclerView.adapter = adapter

    return root
  }
}
