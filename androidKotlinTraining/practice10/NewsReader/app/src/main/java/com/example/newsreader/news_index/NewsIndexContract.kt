package com.example.newsreader.news_index

import com.example.newsreader.BasePresenter
import com.example.newsreader.BaseView

class NewsIndexContract {
  interface View: BaseView<Presenter> {
  }

  interface Presenter: BasePresenter {
  }
}
