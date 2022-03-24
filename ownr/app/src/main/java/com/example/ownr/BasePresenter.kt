package com.example.ownr

interface BasePresenter<T> {
  fun takeView(view: T)

  fun dropView()
}
