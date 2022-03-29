package com.example.ownr.property_list

import android.os.Bundle
import com.example.ownr.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PropertyListActivity : DaggerAppCompatActivity() {
  @Inject lateinit var propertyListFragment: PropertyListFragment

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_content_frame)

    supportFragmentManager.beginTransaction().apply {
      add(R.id.contentFrame, propertyListFragment)
    }.commit()
  }
}
