package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.followed_news.FollowedNewsContract
import com.example.newsreader.followed_news.FollowedNewsPresenter
import com.example.newsreader.news_index.OptionsModalBottomSheet
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FollowedNewsPresenterTest {
  @Mock private lateinit var followedNewsView: FollowedNewsContract.View

  @Mock private lateinit var newsRepository: NewsRepository

  @Mock private lateinit var newsItem: NewsItem

  private val isNewsItemSaved = true

  private lateinit var followedNewsPresenter: FollowedNewsPresenter

  @Before fun setupFollowedNewsPresenter() {
    followedNewsPresenter =
      FollowedNewsPresenter(followedNewsView, newsRepository, TestSchedulerProvider())
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(followedNewsView).presenter = followedNewsPresenter
  }

  @Test fun start_setupsViewWithFollowedNewsItems() {
    val itemsSubject: BehaviorSubject<List<NewsItem>> = BehaviorSubject.create()
    val newsItemList = listOf(newsItem)
    itemsSubject.onNext(newsItemList)

    `when`(newsRepository.followedNewsItemsSubject).thenReturn(itemsSubject)

    followedNewsPresenter.start()

    verify(followedNewsView).showItemsInRecyclerView(newsItemList)
  }

  @Test fun start_setupsViewWithNoItemsMessage() {
    val itemsSubject: BehaviorSubject<List<NewsItem>> = BehaviorSubject.create()
    itemsSubject.onNext(emptyList())

    `when`(newsRepository.followedNewsItemsSubject).thenReturn(itemsSubject)

    followedNewsPresenter.start()

    verify(followedNewsView).showNoFollowedItems()
  }

  @Test fun onClickNewsItem_opensCustomTabInView() {
    followedNewsPresenter.onClickNewsItem(newsItem)

    verify(followedNewsView).openInCustomTab(newsItem.link)
  }

  @Test fun onClickNewsItem_savesToRecentNews() {
    followedNewsPresenter.onClickNewsItem(newsItem)

    verify(newsRepository).addRecentNews(newsItem)
  }

  @Test fun onClickNewsItemOptionsMenu_opensOptionsMenu() {
    `when`(newsRepository.isNewsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    followedNewsPresenter.onClickNewsItemOptionsMenu(newsItem)

    verify(followedNewsView).openOptionsMenu(newsItem, isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_savesNewsItemWhenOptionIsSaveAndItemIsUnsaved() {
    `when`(newsRepository.isNewsFollowed(newsItem)).thenReturn(false)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsRepository).addFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_unsavesNewsItemWhenOptionIsSaveAndItemIsSaved() {
    `when`(newsRepository.isNewsFollowed(newsItem)).thenReturn(true)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsRepository).removeFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_showsToastWhenOptionIsSave() {
    `when`(newsRepository.isNewsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(followedNewsView).showToastForSaveClicked(isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_sharesNewsWhenOptionIsShare() {
    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SHARE)

    verify(followedNewsView).shareNews(newsItem)
  }
}
