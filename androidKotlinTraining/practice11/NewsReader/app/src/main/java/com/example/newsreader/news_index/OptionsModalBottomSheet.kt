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
    private const val ARGUMENT_TITLE = "TITLE"
    private const val ARGUMENT_LINK = "LINK"

    fun newInstance(title: String, link: String) =
      OptionsModalBottomSheet().apply {
        arguments = Bundle().apply {
          putString(ARGUMENT_TITLE, title)
          putString(ARGUMENT_LINK, link)
        }
      }
  }

  private lateinit var title: String
  private lateinit var link: String
  private lateinit var shareOptionTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      title = it.getString(ARGUMENT_TITLE)!!
      link = it.getString(ARGUMENT_LINK)!!
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
    val sendIntent = Intent().apply {
      action = Intent.ACTION_SEND
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, link)
      putExtra(Intent.EXTRA_TITLE, title)
    }
    
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
  }
}
