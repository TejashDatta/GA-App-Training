package com.example.ownr.article_index

import com.example.ownr.di.ActivityScope
import com.example.ownr.di.FragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PropertyIndexModule {
  @FragmentScope
  @ContributesAndroidInjector
  abstract fun propertyIndexFragment(): PropertyIndexFragment

  @ActivityScope
  @Binds abstract fun propertyIndexPresenter(presenter: PropertyIndexPresenter): PropertyIndexContract.Presenter
}
