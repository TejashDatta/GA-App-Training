package com.example.newsreader.news_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsreader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class NewsItemMenuOption { SAVE, SHARE }

class OptionsModalBottomSheet(
  private val optionClickListener: (NewsItemMenuOption) -> Unit
) : BottomSheetDialogFragment() {
  private lateinit var saveOptionTextView: TextView
  private lateinit var shareOptionTextView: TextView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.options_modal_bottom_sheet, container, false)
    saveOptionTextView = root.findViewById(R.id.save_option)
    shareOptionTextView = root.findViewById(R.id.share_option)

    saveOptionTextView.setOnClickListener {
      dismissAllowingStateLoss()
      optionClickListener(NewsItemMenuOption.SAVE)
    }

    shareOptionTextView.setOnClickListener {
      dismissAllowingStateLoss()
      optionClickListener(NewsItemMenuOption.SHARE)
    }

    return root
  }
}
