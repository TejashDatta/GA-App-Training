package com.example.newsreader.news_reader

class NewsReaderPresenter(
  private val newsReaderView: NewsReaderContract.View
): NewsReaderContract.Presenter {
  override fun start() {
    newsReaderView.showAllNews()
  }

  override fun onClickAllNews() {
    newsReaderView.showAllNews()
  }

  override fun onClickGoogleNews() {
    newsReaderView.showGoogleNews()
  }

  override fun onClickToyokeizaiNews() {
    newsReaderView.showToyokeizaiNews()
  }

  override fun onClickFollowedNews() {
    newsReaderView.showFollowedNews()
  }

  override fun onClickRecentNews() {
    newsReaderView.showRecentNews()
  }

  override fun onClickAddNewsSource() {
    newsReaderView.showAddNewsSource()
  }
}
