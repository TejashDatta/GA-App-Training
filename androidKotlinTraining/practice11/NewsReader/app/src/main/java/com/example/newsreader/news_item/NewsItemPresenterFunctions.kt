package com.example.newsreader.news_item

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.news_index.OptionsModalBottomSheet

class NewsItemPresenterFunctions(
  private val newsItemFunctionsView: NewsItemFunctionsContract.View,
  private val followedNewsManager: FollowedNewsManager
): NewsItemFunctionsContract.Presenter {

  override fun onClickNewsItem(newsItem: NewsItem) {
    newsItemFunctionsView.openInCustomTab(newsItem.link)
  }

  override fun onClickNewsItemOptionsMenu(newsItem: NewsItem) {
    newsItemFunctionsView.openOptionsMenu(newsItem, followedNewsManager.isSaved(newsItem))
  }

  override fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option) {
    when(option) {
      OptionsModalBottomSheet.Option.SAVE -> {
        if(followedNewsManager.isSaved(newsItem)) {
          followedNewsManager.remove(newsItem)
        } else {
          followedNewsManager.add(newsItem)
        }
        newsItemFunctionsView.showToastForSaveClicked(followedNewsManager.isSaved(newsItem))
      }

      OptionsModalBottomSheet.Option.SHARE -> newsItemFunctionsView.shareNews(newsItem)
    }
  }
}
