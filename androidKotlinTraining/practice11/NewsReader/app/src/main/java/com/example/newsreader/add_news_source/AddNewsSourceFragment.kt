package com.example.newsreader.add_news_source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsreader.R

class AddNewsSourceFragment: Fragment(), AddNewsSourceContract.View {
  companion object {
    fun newInstance() = AddNewsSourceFragment()
  }

  override lateinit var presenter: AddNewsSourceContract.Presenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.fragment_add_news_source, container, false)
  }

  override fun setNameError(error: String) {
    TODO("Not yet implemented")
  }

  override fun disableNameError() {
    TODO("Not yet implemented")
  }

  override fun setLinkError(error: String) {
    TODO("Not yet implemented")
  }

  override fun disableLinkError() {
    TODO("Not yet implemented")
  }
}
