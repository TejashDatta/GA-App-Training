package com.example.calculator

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class GridMarginItemDecoration(val column_count: Int, context: Context): RecyclerView.ItemDecoration() {
  val column_margin = context.resources.getDimensionPixelSize(R.dimen.gridColumnMargin)
  val row_margin = context.resources.getDimensionPixelSize(R.dimen.gridRowMargin)

  override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
    val column = itemPosition % column_count

    outRect.left = column * column_margin / column_count
    outRect.right = column_margin - (column + 1) * column_margin / column_count

    outRect.bottom = row_margin
  }
}
