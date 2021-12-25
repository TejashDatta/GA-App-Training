package com.example.newsreader.add_news_source

import com.example.newsreader.data.source.NewsItemsRepository

class AddNewsSourcePresenter(
  private val view: AddNewsSourceContract.View,
  private val newsItemsRepository: NewsItemsRepository
): AddNewsSourceContract.Presenter {

  init {
    view.presenter = this
  }

  override fun start() {
    TODO("Not yet implemented")
  }

  override fun validateName(name: String) {
    TODO("Not yet implemented")
  }

  override fun validateUrl(url: String) {
    TODO("Not yet implemented")
  }

  override fun onSaveClick(name: String, url: String) {
    TODO("Not yet implemented")
  }
}
