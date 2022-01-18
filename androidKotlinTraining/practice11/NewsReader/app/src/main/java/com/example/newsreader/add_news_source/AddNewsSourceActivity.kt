package com.example.newsreader.add_news_source

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.newsreader.R
import com.example.newsreader.data.source.NewsItemsRepositoryFactory
import com.example.newsreader.data.validators.AndroidUrlRegexMatcher
import com.example.newsreader.data.validators.NewsSourceValidator

class AddNewsSourceActivity : AppCompatActivity() {
  private lateinit var addNewsSourceFragment: AddNewsSourceFragment
  private lateinit var addNewsSourcePresenter: AddNewsSourcePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    setSupportActionBar(findViewById(R.id.toolbar))

    addNewsSourceFragment = AddNewsSourceFragment.newInstance()

    supportFragmentManager.beginTransaction().apply {
      add(R.id.contentFrame, addNewsSourceFragment)
    }.commit()

    addNewsSourcePresenter = AddNewsSourcePresenter(
      addNewsSourceFragment,
      NewsSourceValidator(AndroidUrlRegexMatcher()),
      NewsItemsRepositoryFactory.getInstance(applicationContext)
    )
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.add_news_source_toolbar_menu, menu)
    addNewsSourcePresenter.isFormValid.subscribe {
      menu?.apply { findItem(R.id.action_save).isEnabled = it }
    }
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_save -> {
        (addNewsSourceFragment as? SaveClickListener)?.onSaveClick()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}

interface SaveClickListener {
  fun onSaveClick()
}
