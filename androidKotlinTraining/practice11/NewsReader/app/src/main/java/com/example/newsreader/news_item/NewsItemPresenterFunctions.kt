package com.example.newsreader.news_item

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.news_index.OptionsModalBottomSheet

class NewsItemPresenterFunctions(
  private val newsItemFunctionsView: NewsItemFunctionsContract.View,
  private val newsRepository: NewsRepository
): NewsItemFunctionsContract.Presenter {

  override fun onClickNewsItem(newsItem: NewsItem) {
    newsItemFunctionsView.openInCustomTab(newsItem.link)
    newsRepository.addRecentNews(newsItem)
  }

  override fun onClickNewsItemOptionsMenu(newsItem: NewsItem) {
    newsItemFunctionsView.openOptionsMenu(newsItem, newsRepository.isNewsFollowed(newsItem))
  }

  override fun onClickNewsItemOption(newsItem: NewsItem, option: OptionsModalBottomSheet.Option) {
    when(option) {
      OptionsModalBottomSheet.Option.SAVE -> {
        if(newsRepository.isNewsFollowed(newsItem)) {
          newsRepository.removeFollowedNews(newsItem)
        } else {
          newsRepository.addFollowedNews(newsItem)
        }
        newsItemFunctionsView.showToastForSaveClicked(newsRepository.isNewsFollowed(newsItem))
      }

      OptionsModalBottomSheet.Option.SHARE -> newsItemFunctionsView.shareNews(newsItem)
    }
  }
}
