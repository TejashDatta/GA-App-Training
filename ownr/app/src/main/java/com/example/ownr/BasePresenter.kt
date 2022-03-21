package com.example.newsreader

interface BasePresenter<T> {
  fun takeView(view: T)

  fun dropView()
}
