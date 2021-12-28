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

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.add_news_source_toolbar_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_save -> {
        //TODO: implement click listener in fragment 
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
