package com.example.newsreader.news_item

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.news_index.OptionsModalBottomSheet

class NewsItemPresenterFunctions(
  private val newsItemFunctionsView: NewsItemFunctionsContract.View,
  private val newsItemsRepository: NewsItemsRepository
): NewsItemFunctionsContract.Presenter {

  override fun onClickNewsItem(newsItem: NewsItem) {
    newsItemFunctionsView.openInTab(newsItem.link)
    newsItemsRepository.addRecentNews(newsItem)
  }

  override fun onClickNewsItemOptionsMenu(newsItem: NewsItem) {
    newsItemFunctionsView.openOptionsMenu(newsItem, newsItemsRepository.newsIsFollowed(newsItem))
  }

  override fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option) {
    when(option) {
      OptionsModalBottomSheet.Option.SAVE -> {
        if(newsItemsRepository.newsIsFollowed(newsItem)) {
          newsItemsRepository.removeFollowedNews(newsItem)
        } else {
          newsItemsRepository.addFollowedNews(newsItem)
        }
        newsItemFunctionsView.showToastForSaveClicked(newsItemsRepository.newsIsFollowed(newsItem))
      }

      OptionsModalBottomSheet.Option.SHARE -> newsItemFunctionsView.shareNews(newsItem)
    }
  }
}
