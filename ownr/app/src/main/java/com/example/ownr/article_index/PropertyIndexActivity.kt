package com.example.ownr.article_index

import android.os.Bundle
import com.example.ownr.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PropertyIndexActivity : DaggerAppCompatActivity() {
  @Inject lateinit var propertyIndexFragment: PropertyIndexFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    supportFragmentManager.beginTransaction().apply {
      add(R.id.contentFrame, propertyIndexFragment)
    }.commit()
  }
}
