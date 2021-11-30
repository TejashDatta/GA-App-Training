package com.example.newsreader

import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.data.source.FollowedNewsManager
import com.example.newsreader.data.source.RecentNewsManager
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

  @Mock private lateinit var followedNewsManager: FollowedNewsManager
  @Mock private lateinit var recentNewsManager: RecentNewsManager

  @Mock private lateinit var newsItem: NewsItem

  private val isNewsItemSaved = true

  private lateinit var newsItemPresenterFunctions: NewsItemPresenterFunctions

  @Before fun setupNewsItemPresenterFunctions() {
    newsItemPresenterFunctions =
      NewsItemPresenterFunctions(newsItemFunctionsView, followedNewsManager, recentNewsManager)
  }

  @Test fun onClickNewsItem_opensTabInView() {
    newsItemPresenterFunctions.onClickNewsItem(newsItem)

    verify(newsItemFunctionsView).openInTab(newsItem.link)
  }

  @Test fun onClickNewsItem_savesToRecentNews() {
    newsItemPresenterFunctions.onClickNewsItem(newsItem)

    verify(recentNewsManager).add(newsItem)
  }

  @Test fun onClickNewsItemOptionsMenu_opensOptionsMenu() {
    `when`(followedNewsManager.isSaved(newsItem)).thenReturn(isNewsItemSaved)

    newsItemPresenterFunctions.onClickNewsItemOptionsMenu(newsItem)

    verify(newsItemFunctionsView).openOptionsMenu(newsItem, isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_savesNewsItemWhenOptionIsSaveAndItemIsUnsaved() {
    `when`(followedNewsManager.isSaved(newsItem)).thenReturn(false)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(followedNewsManager).add(newsItem)
  }

  @Test fun onClickNewsItemOption_unsavesNewsItemWhenOptionIsSaveAndItemIsSaved() {
    `when`(followedNewsManager.isSaved(newsItem)).thenReturn(true)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(followedNewsManager).remove(newsItem)
  }

  @Test fun onClickNewsItemOption_showsToastWhenOptionIsSave() {
    `when`(followedNewsManager.isSaved(newsItem)).thenReturn(isNewsItemSaved)

    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SAVE)

    verify(newsItemFunctionsView).showToastForSaveClicked(isNewsItemSaved)
  }

  @Test fun onClickNewsItemOption_sharesNewsWhenOptionIsShare() {
    newsItemPresenterFunctions.onClickNewsItemOption(newsItem, OptionsModalBottomSheet.Option.SHARE)

    verify(newsItemFunctionsView).shareNews(newsItem)
  }
}
