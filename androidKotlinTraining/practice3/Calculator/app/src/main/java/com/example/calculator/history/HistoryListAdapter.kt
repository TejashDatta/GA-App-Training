package com.example.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.HistoryListItemBinding

class HistoryListAdapter(
  private val items: List<String>,
  private val onHistorySelected: (String) -> Unit
): RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {
  class ViewHolder(val binding: HistoryListItemBinding): RecyclerView.ViewHolder(binding.root)

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, _viewType: Int): ViewHolder {
    return ViewHolder(HistoryListItemBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val history = items[position]
    holder.binding.historyText.apply {
      text = history
      setOnClickListener { onHistorySelected(history) }
    }
  }
}
