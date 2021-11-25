package com.example.newsreader.followed_news

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_item.NewsItemFunctionsContract

class FollowedNewsContract {
  interface View: BaseView<Presenter>, NewsItemFunctionsContract.View {
    fun showItemsInRecyclerView(newsItems: List<NewsItem>)
    fun showNoFollowedItems()
  }

  interface Presenter: BasePresenter, NewsItemFunctionsContract.Presenter
}
