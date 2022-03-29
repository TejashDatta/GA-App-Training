package com.example.ownr.property_list

import com.example.ownr.di.ActivityScope
import com.example.ownr.di.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PropertyListModule {
  @FragmentScope
  @ContributesAndroidInjector
  abstract fun propertyListFragment(): PropertyListFragment

  @ActivityScope
  @Binds abstract fun propertyListPresenter(presenter: PropertyListPresenter): PropertyListContract.Presenter
}
