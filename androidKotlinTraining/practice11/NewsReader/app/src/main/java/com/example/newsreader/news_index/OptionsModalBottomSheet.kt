package com.example.newsreader.news_index

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsreader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionsModalBottomSheet : BottomSheetDialogFragment() {
  companion object {
    private const val ARGUMENT_NEWS_ITEM_TITLE = "NEWS_ITEM_TITLE"
    private const val ARGUMENT_NEWS_ITEM_LINK = "NEWS_ITEM_LINK"

    @JvmStatic fun newInstance(newsItemTitle: String, newsItemLink: String) =
      OptionsModalBottomSheet().apply {
        arguments = Bundle().apply {
          putString(ARGUMENT_NEWS_ITEM_TITLE, newsItemTitle)
          putString(ARGUMENT_NEWS_ITEM_LINK, newsItemLink)
        }
      }
  }

  private lateinit var newsItemTitle: String
  private lateinit var newsItemLink: String
  private lateinit var shareOptionTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      newsItemTitle = it.getString(ARGUMENT_NEWS_ITEM_TITLE)!!
      newsItemLink = it.getString(ARGUMENT_NEWS_ITEM_LINK)!!
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.options_modal_bottom_sheet, container, false)
    shareOptionTextView = root.findViewById(R.id.share_option)

    shareOptionTextView.setOnClickListener {
      dismissAllowingStateLoss()
      shareNews()
    }

    return root
  }

  private fun shareNews() {
    val shareIntent = Intent.createChooser(Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, newsItemLink)
      putExtra(Intent.EXTRA_TITLE, newsItemTitle)
    }, null)
    startActivity(shareIntent)
  }
}
