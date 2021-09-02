package com.example.calculator.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.HistoryListItemBinding

class HistoryListAdapter: RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {
  class ViewHolder(val binding: HistoryListItemBinding): RecyclerView.ViewHolder(binding.root)

  var items = listOf("3 + 5 = 8", "12 * 5 = 60", "1 - 1 = 0", "2 * 0.25 = 0.5")

  override fun getItemCount() = items.size

  override fun onCreateViewHolder(parent: ViewGroup, _viewType: Int): ViewHolder {
    return ViewHolder(HistoryListItemBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.expression = items[position]
    holder.binding.executePendingBindings()
  }
}

