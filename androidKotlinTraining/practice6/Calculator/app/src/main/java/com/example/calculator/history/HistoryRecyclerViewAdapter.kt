package com.example.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class HistoryRecyclerViewAdapter(
  private val items: List<String>,
  private val onHistorySelected: (String) -> Unit
): RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

  class ViewHolder private constructor(val historyText: TextView): RecyclerView.ViewHolder(historyText) {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val historyText = layoutInflater.inflate(R.layout.history_recycler_view_item, parent, false) as TextView
        return ViewHolder(historyText)
      }
    }
  }

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, _viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    val history = items[position]
    viewHolder.historyText.apply {
      text = history
      setOnClickListener { onHistorySelected(history) }
    }
  }
}
