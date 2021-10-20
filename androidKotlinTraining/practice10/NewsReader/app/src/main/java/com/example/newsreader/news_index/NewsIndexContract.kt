package com.example.newsreader.news_index

import com.example.android.architecture.blueprints.todoapp.BasePresenter
import com.example.android.architecture.blueprints.todoapp.BaseView

class NewsIndexContract {
  interface View: BaseView<Presenter> {
  }

  interface Presenter: BasePresenter {
  }
}
