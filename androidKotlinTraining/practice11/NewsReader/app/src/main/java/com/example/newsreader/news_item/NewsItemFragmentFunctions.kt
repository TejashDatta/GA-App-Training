package com.example.newsreader.news_item

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.example.newsreader.R
import com.example.newsreader.data.models.NewsItem
import com.example.newsreader.news_index.OptionsModalBottomSheet

class NewsItemFragmentFunctions(
  var newsItemFunctionsPresenter: NewsItemFunctionsContract.Presenter
) : Fragment(), NewsItemFunctionsContract.View {

  override fun openInTab(url: String) {
    CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(url))
  }

  override fun openOptionsMenu(newsItem: NewsItem, isNewsItemSaved: Boolean) {
    OptionsModalBottomSheet(isNewsItemSaved) { option -> newsItemFunctionsPresenter.onClickNewsItemOption(newsItem, option) }
      .show(childFragmentManager, tag)
  }

  override fun shareNews(newsItem: NewsItem) {
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, newsItem.link)
      putExtra(Intent.EXTRA_TITLE, newsItem.title)
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }

  override fun showToastForSaveClicked(isSaved: Boolean) {
    val messageResourceID = if (isSaved) R.string.item_followed else R.string.item_unfollowed

    Toast.makeText(context, messageResourceID, Toast.LENGTH_SHORT).show()
  }
}
