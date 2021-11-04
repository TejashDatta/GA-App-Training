package com.example.newsreader.news_index

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.util.replaceFragmentInActivity
import com.example.newsreader.R
import com.example.newsreader.SchedulerProvider
import com.example.newsreader.data.NewsItemsRepositoryLocator

class NewsIndexActivity : AppCompatActivity() {
  private lateinit var newsIndexPresenter: NewsIndexPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    val newsIndexFragment =
      supportFragmentManager.findFragmentById(R.id.contentFrame) as NewsIndexFragment?
        ?: NewsIndexFragment.newInstance().also {
          replaceFragmentInActivity(R.id.contentFrame, it)
        }

    newsIndexPresenter =
      NewsIndexPresenter(newsIndexFragment, NewsItemsRepositoryLocator.repository, SchedulerProvider())
  }
}
