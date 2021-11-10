package com.example.newsreader.news_index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.newsreader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionsModalBottomSheet : BottomSheetDialogFragment() {
  companion object {
//    TODO: take news title and url as argument
    fun newInstance() = OptionsModalBottomSheet()
  }

  private lateinit var shareOption: TextView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    val root = inflater.inflate(R.layout.options_modal_bottom_sheet, container, false)
    shareOption = root.findViewById(R.id.share_option)

    shareOption.setOnClickListener {
      dismissAllowingStateLoss()
      shareNews()
    }

    return root
  }

  private fun shareNews() {
//    TODO: implement sharing
    Toast.makeText(context, "Share news", Toast.LENGTH_LONG).show()
  }
}
