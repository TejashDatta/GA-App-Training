package com.example.ownr.property_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ownr.R
import com.example.ownr.di.ActivityScope
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScope
class PropertyListFragment @Inject constructor(): DaggerFragment(), PropertyListContract.View {
  @Inject lateinit var presenter: PropertyListContract.Presenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_property_list, container, false)
  }
}
