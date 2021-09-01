package com.example.calculator.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.GridViewButtonBinding

class ButtonGridAdapter(
  private val operandClickListener: (Char) -> Unit,
  private val operatorClickListener: (Char) -> Unit,
  private val decimalPointClickListener: () -> Unit,
  private val resultClickListener: () -> Unit
): RecyclerView.Adapter<ButtonGridAdapter.ViewHolder>() {
  class ViewHolder(private val binding: GridViewButtonBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonLabel: Char) {
      binding.buttonLabel = buttonLabel.toString()
      binding.executePendingBindings()
    }
  }

  private val buttonLabels = charArrayOf(
    '+', '9', '8', '7',
    '-', '6', '5', '4',
    '*', '3', '2', '1',
    '/', '=', '0', '.'
  )

  override fun getItemCount() = buttonLabels.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(GridViewButtonBinding.inflate(LayoutInflater.from(parent.context)))
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    val buttonLabel = buttonLabels[position]
    viewHolder.bind(buttonLabel)
    viewHolder.itemView.setOnClickListener { onClickButton(buttonLabel) }
  }

  private fun onClickButton(buttonLabel: Char) {
    when(buttonLabel) {
      in '0'..'9' -> operandClickListener(buttonLabel)
      '+', '-', '*', '/' -> operatorClickListener(buttonLabel)
      '.' -> decimalPointClickListener()
      '=' -> resultClickListener()
      else -> throw Exception("No click listener for button label $buttonLabel")
    }
  }
}
