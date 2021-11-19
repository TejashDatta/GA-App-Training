package com.example.newsreader.news_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsreader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionsModalBottomSheet(
  private val isNewsItemSaved: Boolean,
  private val optionClickListener: (Option) -> Unit
) : BottomSheetDialogFragment() {
  private lateinit var saveOptionTextView: TextView
  private lateinit var shareOptionTextView: TextView

  enum class Option { SAVE, SHARE }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.options_modal_bottom_sheet, container, false)
    saveOptionTextView = root.findViewById(R.id.save_option)
    shareOptionTextView = root.findViewById(R.id.share_option)

    setSaveOptionTextAndIcon()

    saveOptionTextView.setOnClickListener {
      dismissAllowingStateLoss()
      optionClickListener(Option.SAVE)
    }

    shareOptionTextView.setOnClickListener {
      dismissAllowingStateLoss()
      optionClickListener(Option.SHARE)
    }

    return root
  }

  private fun setSaveOptionTextAndIcon() {
    if (isNewsItemSaved) {
      saveOptionTextView.text = getString(R.string.remove_from_followed_items)
      saveOptionTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_bookmark_24, 0, 0, 0)
    } else {
      saveOptionTextView.text = getString(R.string.save_for_later)
      saveOptionTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_bookmark_border_24, 0, 0, 0)
    }
  }
}
