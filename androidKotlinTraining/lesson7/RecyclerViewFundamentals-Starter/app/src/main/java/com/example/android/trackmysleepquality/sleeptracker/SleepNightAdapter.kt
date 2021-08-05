package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import java.lang.ClassCastException

enum class ViewType {
  HEADER,
  ITEM
}

class SleepNightAdapter(private val clickListener: SleepNightListener):
  ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback())
{
  class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
      fun from(parent: ViewGroup): TextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.header, parent, false)
        return TextViewHolder(view)
      }
    }
  }

  class ViewHolder private constructor(val binding: ListItemSleepNightBinding):
    RecyclerView.ViewHolder(binding.root)
  {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
      }
    }

    fun bind(item: SleepNight, clickListener: SleepNightListener) {
      binding.sleep = item
      binding.clickListener = clickListener
      binding.executePendingBindings()
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is DataItem.Header -> ViewType.HEADER.ordinal
      is DataItem.SleepNightItem -> ViewType.ITEM.ordinal
      else -> throw ClassCastException("not a data item")
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when(viewType) {
      ViewType.HEADER.ordinal -> TextViewHolder.from(parent)
      ViewType.ITEM.ordinal  -> ViewHolder.from(parent)
      else -> throw ClassCastException("Unknown viewType ${viewType}")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ViewHolder -> {
        val nightItem = getItem(position) as DataItem.SleepNightItem
        holder.bind(nightItem.sleepNight, clickListener)
      }
    }
  }

  fun addHeaderAndSubmitList(list: List<SleepNight>?) {
    val items = when(list) {
      null -> listOf(DataItem.Header)
      else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
    }
    submitList(items)
  }
}

class SleepNightDiffCallback: DiffUtil.ItemCallback<DataItem>() {
  override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
    return oldItem == newItem
  }
}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
  fun onClick(night: SleepNight) = clickListener(night.nightId)
}

sealed class DataItem {
  abstract val id: Long

  object Header: DataItem() {
    override val id = Long.MIN_VALUE
  }

  data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
    override val id = sleepNight.nightId
  }
}
