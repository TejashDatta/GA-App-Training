package com.example.newsreader.add_news_source

import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsItemsRepository

class AddNewsSourcePresenter(
  private val addNewsSourceView: AddNewsSourceContract.View,
  private val newsItemsRepository: NewsItemsRepository
): AddNewsSourceContract.Presenter {

  init {
    addNewsSourceView.presenter = this
  }

  override fun start() {
    TODO("Not yet implemented")
  }

  override fun validateName(name: String) {
    TODO("Not yet implemented")
  }

  override fun validateLink(link: String) {
    TODO("Not yet implemented")
  }

  override fun addNewsSource(newsSource: NewsSource) {
    TODO("Not yet implemented")
  }
}
