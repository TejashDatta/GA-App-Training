package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsItemsRepository
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

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  @Mock private lateinit var newsItem: NewsItem

  private val isNewsItemSaved = true

  private lateinit var followedNewsPresenter: FollowedNewsPresenter

  @Before fun setupFollowedNewsPresenter() {
    followedNewsPresenter =
      FollowedNewsPresenter(followedNewsView, newsItemsRepository, TestSchedulerProvider())
  }

  @Test fun createPresenter_setsPresenterToView() {
    verify(followedNewsView).presenter = followedNewsPresenter
  }

  @Test fun start_setupsViewWithFollowedNewsItems() {
    val itemsSubject: BehaviorSubject<List<NewsItem>> = BehaviorSubject.create()
    val newsItemList = listOf(newsItem)
    itemsSubject.onNext(newsItemList)

    `when`(newsItemsRepository.followedNewsItemsSubject).thenReturn(itemsSubject)

    followedNewsPresenter.start()

    verify(followedNewsView).showItemsInRecyclerView(newsItemList)
  }

  @Test fun start_setupsViewWithNoItemsMessage() {
    val itemsSubject: BehaviorSubject<List<NewsItem>> = BehaviorSubject.create()
    itemsSubject.onNext(emptyList())

    `when`(newsItemsRepository.followedNewsItemsSubject).thenReturn(itemsSubject)

    followedNewsPresenter.start()

    verify(followedNewsView).showNoFollowedItems()
  }

  @Test fun onClickNewsItem_opensTabInView() {
    followedNewsPresenter.onClickNewsItem(newsItem)

    verify(followedNewsView).openInTab(newsItem.link)
  }

  @Test fun onClickNewsItem_savesToRecentNews() {
    followedNewsPresenter.onClickNewsItem(newsItem)

    verify(newsItemsRepository).addRecentNews(newsItem)
  }

  @Test fun onClickNewsItemOptionsMenu_opensOptionsMenu() {
    `when`(newsItemsRepository.newsIsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    followedNewsPresenter.onClickNewsItemOptionsMenu(newsItem)

    verify(followedNewsView).openOptionsMenu(newsItem, isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_savesNewsItemWhenOptionIsSaveAndItemIsUnsaved() {
    `when`(newsItemsRepository.newsIsFollowed(newsItem)).thenReturn(false)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemsRepository).addFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_unsavesNewsItemWhenOptionIsSaveAndItemIsSaved() {
    `when`(newsItemsRepository.newsIsFollowed(newsItem)).thenReturn(true)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemsRepository).removeFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_showsToastWhenOptionIsSave() {
    `when`(newsItemsRepository.newsIsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(followedNewsView).showToastForSaveClicked(isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_sharesNewsWhenOptionIsShare() {
    followedNewsPresenter.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SHARE)

    verify(followedNewsView).shareNews(newsItem)
  }
}
