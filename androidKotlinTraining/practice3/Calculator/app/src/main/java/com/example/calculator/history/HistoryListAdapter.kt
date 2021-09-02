package com.example.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class HistoryListAdapter(
  private val items: List<String>,
  private val onHistorySelected: (String) -> Unit
): RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {
  class ViewHolder private constructor(val view: TextView): RecyclerView.ViewHolder(view) {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.history_list_item, parent, false) as TextView
        return ViewHolder(view)
      }
    }
  }

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, _viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val history = items[position]
    holder.view.apply {
      text = history
      setOnClickListener { onHistorySelected.invoke(history) }
    }
  }
}

