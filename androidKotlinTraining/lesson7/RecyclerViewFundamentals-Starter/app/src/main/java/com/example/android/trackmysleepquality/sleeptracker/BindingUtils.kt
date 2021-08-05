package com.example.android.trackmysleepquality.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
  val safeItem = item ?: return
  text = convertDurationToFormatted(safeItem.startTimeMilli, safeItem.endTimeMilli, context.resources)
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
  val safeItem = item ?: return
  text = convertNumericQualityToString(safeItem.sleepQuality, context.resources)
}

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?) {
  val safeItem = item ?: return
  setImageResource(
    when (safeItem.sleepQuality) {
      0 -> R.drawable.ic_sleep_0
      1 -> R.drawable.ic_sleep_1
      2 -> R.drawable.ic_sleep_2
      3 -> R.drawable.ic_sleep_3
      4 -> R.drawable.ic_sleep_4
      5 -> R.drawable.ic_sleep_5
      else -> R.drawable.ic_sleep_active
    }
  )
}

