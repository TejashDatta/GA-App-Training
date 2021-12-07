package com.example.newsreader.news_item

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.OptionsModalBottomSheet

class NewsItemFunctionsContract {
  interface View {
    fun openInCustomTab(url: String)
    fun openOptionsMenu(newsItem: NewsItem, isNewsItemSaved: Boolean)
    fun shareNews(newsItem: NewsItem)
    fun showToastForSaveClicked(isSaved: Boolean)
  }

  interface Presenter {
    fun onClickNewsItem(newsItem: NewsItem)
    fun onClickNewsItemOptionsMenu(newsItem: NewsItem)
    fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option)
  }
}
