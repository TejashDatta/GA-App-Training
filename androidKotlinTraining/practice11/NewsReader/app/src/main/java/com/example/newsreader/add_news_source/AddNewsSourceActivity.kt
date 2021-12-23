package com.example.newsreader.add_news_source

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsreader.R
import com.example.newsreader.data.source.NewsItemsRepositoryFactory

class AddNewsSourceActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    setSupportActionBar(findViewById(R.id.toolbar))

    val addNewsSourceFragment = AddNewsSourceFragment.newInstance()

    supportFragmentManager.beginTransaction().apply {
      add(R.id.contentFrame, addNewsSourceFragment)
    }.commit()

    AddNewsSourcePresenter(
      addNewsSourceFragment,
      NewsItemsRepositoryFactory.getInstance(applicationContext)
    )
  }
}
