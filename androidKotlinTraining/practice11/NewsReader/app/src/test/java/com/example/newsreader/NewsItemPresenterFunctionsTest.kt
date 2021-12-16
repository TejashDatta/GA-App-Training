package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.NewsItemsRepository
import com.example.newsreader.news_index.OptionsModalBottomSheet
import com.example.newsreader.news_item.NewsItemFunctionsContract
import com.example.newsreader.news_item.NewsItemPresenterFunctions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsItemPresenterFunctionsTest {
  @Mock private lateinit var newsItemFunctionsView: NewsItemFunctionsContract.View

  @Mock private lateinit var newsItemsRepository: NewsItemsRepository

  @Mock private lateinit var newsItem: NewsItem

  private val isNewsItemSaved = true

  private lateinit var newsItemPresenterFunctions: NewsItemPresenterFunctions

  @Before fun setupNewsItemPresenterFunctions() {
    newsItemPresenterFunctions =
      NewsItemPresenterFunctions(newsItemFunctionsView, newsItemsRepository)
  }

  @Test fun onClickNewsItem_opensCustomTabInView() {
    newsItemPresenterFunctions.onClickNewsItem(newsItem)

    verify(newsItemFunctionsView).openInCustomTab(newsItem.link)
  }

  @Test fun onClickNewsItem_savesToRecentNews() {
    newsItemPresenterFunctions.onClickNewsItem(newsItem)

    verify(newsItemsRepository).addRecentNews(newsItem)
  }

  @Test fun onClickNewsItemOptionsMenu_opensOptionsMenu() {
    `when`(newsItemsRepository.isNewsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    newsItemPresenterFunctions.onClickNewsItemOptionsMenu(newsItem)

    verify(newsItemFunctionsView).openOptionsMenu(newsItem, isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_savesNewsItemWhenOptionIsSaveAndItemIsUnsaved() {
    `when`(newsItemsRepository.isNewsFollowed(newsItem)).thenReturn(false)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemsRepository).addFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_unsavesNewsItemWhenOptionIsSaveAndItemIsSaved() {
    `when`(newsItemsRepository.isNewsFollowed(newsItem)).thenReturn(true)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemsRepository).removeFollowedNews(newsItem)
  }

  @Test fun onClickNewsItemOption_showsToastWhenOptionIsSave() {
    `when`(newsItemsRepository.isNewsFollowed(newsItem)).thenReturn(isNewsItemSaved)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemFunctionsView).showToastForSaveClicked(isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_sharesNewsWhenOptionIsShare() {
    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SHARE)

    verify(newsItemFunctionsView).shareNews(newsItem)
  }
}
