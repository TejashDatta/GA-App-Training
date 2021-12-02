package com.example.newsreader.news_index

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_item.NewsItemFunctionsContract

class NewsIndexContract {
  interface View: BaseView<Presenter>, NewsItemFunctionsContract.View {
    fun showItemsInRecyclerView(newsItems: List<NewsItem>)
    fun showLoading()
    fun showError()
    fun showNoResults()
  }

  interface Presenter: BasePresenter, NewsItemFunctionsContract.Presenter {
    fun end()
    fun refreshNewsItems()
  }
}
