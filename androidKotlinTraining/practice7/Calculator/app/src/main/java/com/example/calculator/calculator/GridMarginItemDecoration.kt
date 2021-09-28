package com.example.calculator.calculator

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class GridMarginItemDecoration(private val columnCount: Int, context: Context): RecyclerView.ItemDecoration() {
  private val columnMargin = context.resources.getDimensionPixelSize(R.dimen.gridColumnMargin)
  private val rowMargin = context.resources.getDimensionPixelSize(R.dimen.gridRowMargin)

  override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
    val column = itemPosition % columnCount

    outRect.left = column * columnMargin / columnCount
    outRect.right = columnMargin - (column + 1) * columnMargin / columnCount

    outRect.bottom = rowMargin
  }
}
