package com.example.newsreader.news_index

class NewsIndexPresenter(
  private val newsIndexView: NewsIndexContract.View): NewsIndexContract.Presenter {

  init {
    newsIndexView.presenter = this
  }

  //  TODO: implement function later
  override fun start() {
  }
}