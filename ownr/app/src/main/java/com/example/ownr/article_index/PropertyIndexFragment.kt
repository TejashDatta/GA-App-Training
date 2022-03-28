package com.example.ownr.article_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ownr.R
import com.example.ownr.di.ActivityScope
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScope
class PropertyIndexFragment @Inject constructor(): DaggerFragment(), PropertyIndexContract.View {
  @Inject lateinit var presenter: PropertyIndexContract.Presenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_property_index, container, false)
  }
}
