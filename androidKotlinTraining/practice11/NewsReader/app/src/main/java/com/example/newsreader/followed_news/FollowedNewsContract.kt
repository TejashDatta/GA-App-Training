package com.example.newsreader.followed_news

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.OptionsModalBottomSheet

class FollowedNewsContract {
  interface View: BaseView<Presenter> {
    fun showItemsInRecyclerView(newsItems: List<NewsItem>)
    fun openInCustomTab(url: String)
    fun openOptionsMenu(newsItem: NewsItem, isNewsItemSaved: Boolean)
    fun shareNews(newsItem: NewsItem)
    fun showToastForSaveClicked(isSaved: Boolean)
    fun showNoFollowedItems()
  }

  interface Presenter: BasePresenter {
    fun onClickNewsItem(newsItem: NewsItem)
    fun onClickNewsItemOptionsMenu(newsItem: NewsItem)
    fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option)
  }
}
