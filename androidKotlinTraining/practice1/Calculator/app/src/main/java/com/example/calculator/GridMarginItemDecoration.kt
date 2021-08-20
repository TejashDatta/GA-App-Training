package com.example.calculator

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class GridMarginItemDecoration(val columns: Int): RecyclerView.ItemDecoration() {
  companion object {
    val COLUMN_MARGIN = 30
    val ROW_MARGIN = 15
  }

  override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
    if (itemPosition % columns != 0) outRect.left = COLUMN_MARGIN
    outRect.bottom = ROW_MARGIN
  }
}
