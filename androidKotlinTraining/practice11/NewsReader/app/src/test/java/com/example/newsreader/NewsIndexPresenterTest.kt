package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.models.NewsSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.StockNewsSources
import com.example.newsreader.news_index.NewsIndexContract
import com.example.newsreader.news_index.NewsIndexPresenter
import com.example.newsreader.news_index.OptionsModalBottomSheet
import io.reactivex.rxjava3.core.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsIndexPresenterTest {
  @Mock private lateinit var newsIndexView: NewsIndexContract.View

  @Mock private lateinit var newsRepository: NewsRepository

  @Mock private lateinit var newsItem1: NewsItem
  @Mock private lateinit var newsItem2: NewsItem

  private val isNewsItemSaved = true

  private lateinit var newsIndexPresenter: NewsIndexPresenter

  private val stockNewsSources = StockNewsSources()

  private fun createPresenter(newsSource: NewsSource) {
    newsIndexPresenter =
      NewsIndexPresenter(newsSource, newsIndexView, newsRepository, TestSchedulerProvider())
  }

  private fun createPresenterWithAllSources() = createPresenter(stockNewsSources.all)

  @Test fun createPresenter_setsPresenterToView() {
    createPresenterWithAllSources()
    verify(newsIndexView).presenter = newsIndexPresenter
  }

  @Test fun onClickNewsItem_opensCustomTabInView() {
    createPresenterWithAllSources()
    newsIndexPresenter.onClickNewsItem(newsItem1)

    verify(newsIndexView).openInCustomTab(newsItem1.link)
  }

  @Test fun onClickNewsItem_savesToRecentNews() {
    createPresenterWithAllSources()
    newsIndexPresenter.onClickNewsItem(newsItem1)

    verify(newsRepository).addRecentNews(newsItem1)
  }

  @Test fun onClickNewsItemOptionsMenu_opensOptionsMenu() {
    createPresenterWithAllSources()
    `when`(newsRepository.isNewsFollowed(newsItem1)).thenReturn(isNewsItemSaved)

    newsIndexPresenter.onClickNewsItemOptionsMenu(newsItem1)

    verify(newsIndexView).openOptionsMenu(newsItem1, isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_savesNewsItemWhenOptionIsSaveAndItemIsUnsaved() {
    createPresenterWithAllSources()
    `when`(newsRepository.isNewsFollowed(newsItem1)).thenReturn(false)

    newsIndexPresenter.onClickNewsItemOption(newsItem1, OptionsModalBottomSheet.Option.SAVE)

    verify(newsRepository).addFollowedNews(newsItem1)
  }

  @Test fun onClickNewsItemOption_unsavesNewsItemWhenOptionIsSaveAndItemIsSaved() {
    createPresenterWithAllSources()
    `when`(newsRepository.isNewsFollowed(newsItem1)).thenReturn(true)

    newsIndexPresenter.onClickNewsItemOption(newsItem1, OptionsModalBottomSheet.Option.SAVE)

    verify(newsRepository).removeFollowedNews(newsItem1)
  }

  @Test fun onClickNewsItemOption_showsToastWhenOptionIsSave() {
    createPresenterWithAllSources()
    `when`(newsRepository.isNewsFollowed(newsItem1)).thenReturn(isNewsItemSaved)

    newsIndexPresenter.onClickNewsItemOption(newsItem1, OptionsModalBottomSheet.Option.SAVE)

    verify(newsIndexView).showToastForSaveClicked(isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_sharesNewsWhenOptionIsShare() {
    createPresenterWithAllSources()
    newsIndexPresenter.onClickNewsItemOption(newsItem1, OptionsModalBottomSheet.Option.SHARE)

    verify(newsIndexView).shareNews(newsItem1)
  }

  @Test fun start_getsNewsFromRepositoryWhenNewsSourceIsAll() {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = false))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.start()

    verify(newsRepository).getNews(stockNewsSources.all, refresh = false)
  }

  @Test fun start_getsNewsFromRepositoryWhenNewsSourceIsGoogle() {
    createPresenter(stockNewsSources.google)
    `when`(newsRepository.getNews(stockNewsSources.google, refresh = false))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.start()

    verify(newsRepository).getNews(stockNewsSources.google, refresh = false)
  }

  @Test fun start_getsNewsFromRepositoryWhenNewsSourceIsToyokeizai() {
    createPresenter(stockNewsSources.toyokeizai)
    `when`(newsRepository.getNews(stockNewsSources.toyokeizai, refresh = false))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.start()

    verify(newsRepository).getNews(stockNewsSources.toyokeizai, refresh = false)
  }

  @Test fun start_showsItemsInRecyclerViewWhenThereAreResults() {
    createPresenterWithAllSources()
    val resultsList = listOf(newsItem1, newsItem2)
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = false))
      .thenReturn(Observable.just(resultsList))

    newsIndexPresenter.start()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showItemsInRecyclerView(resultsList)
  }

  @Test fun start_showNoResultsMessageWhenThereAreNoResults() {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = false))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.start()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showNoResults()
  }

  @Test fun start_showErrorMessageWhenError () {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = false))
      .thenReturn(Observable.error(RuntimeException("example error")))

    newsIndexPresenter.start()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showError()
  }

  @Test fun refreshNews_getsNewsFromRepositoryWhenNewsSourceIsAll() {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = true))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNews()

    verify(newsRepository).getNews(stockNewsSources.all, refresh = true)
  }

  @Test fun refreshNews_getsNewsFromRepositoryWhenNewsSourceIsGoogle() {
    createPresenter(stockNewsSources.google)
    `when`(newsRepository.getNews(stockNewsSources.google, refresh = true))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNews()

    verify(newsRepository).getNews(stockNewsSources.google, refresh = true)
  }

  @Test fun refreshNews_getsNewsFromRepositoryWhenNewsSourceIsToyokeizai() {
    createPresenter(stockNewsSources.toyokeizai)
    `when`(newsRepository.getNews(stockNewsSources.toyokeizai, refresh = true))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNews()

    verify(newsRepository).getNews(stockNewsSources.toyokeizai, refresh = true)
  }

  @Test fun refreshNews_showItemsInRecyclerViewWhenThereAreResults() {
    createPresenterWithAllSources()
    val resultsList = listOf(newsItem1, newsItem2)
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = true))
      .thenReturn(Observable.just(resultsList))

    newsIndexPresenter.refreshNews()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showItemsInRecyclerView(resultsList)
  }

  @Test fun refreshNews_showNoResultsMessageWhenThereAreNoResults() {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = true))
      .thenReturn(Observable.just(emptyList()))

    newsIndexPresenter.refreshNews()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showNoResults()
  }

  @Test fun refreshNews_showErrorMessageWhenError () {
    createPresenterWithAllSources()
    `when`(newsRepository.getNews(stockNewsSources.all, refresh = true))
      .thenReturn(Observable.error(RuntimeException("example error")))

    newsIndexPresenter.refreshNews()

    verify(newsIndexView).showLoading()
    verify(newsIndexView).showError()
  }
}
