package com.example.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.databinding.GridViewButtonBinding

class ButtonGridAdapter(
  private val operandClickListener: OperandClickListener,
  private val operatorClickListener: OperatorClickListener,
  private val decimalPointClickListener: DecimalPointClickListener,
  private val resultClickListener: ResultClickListener
): RecyclerView.Adapter<ButtonGridAdapter.ViewHolder>() {
  class ViewHolder(private val binding: GridViewButtonBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(buttonLabel: Char) {
      binding.buttonLabel = buttonLabel.toString()
      binding.executePendingBindings()
    }
  }

  //TODO: class の代わりに interface を使おう

  class OperandClickListener(val clickListener: (operand: Char) -> Unit) {
    fun onClick(operand: Char) = clickListener(operand)
  }

  class OperatorClickListener(val clickListener: (operator: Char) -> Unit) {
    fun onClick(operator: Char) = clickListener(operator)
  }

  class DecimalPointClickListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
  }

  class ResultClickListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
  }

  private val buttonLabels = charArrayOf(
    '+', '9', '8', '7',
    '-', '6', '5', '4',
    '*', '3', '2', '1',
    '%', '=', '0', '.'
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
      in '0'..'9' -> operandClickListener.onClick(buttonLabel)
      '+', '-', '%', '*' -> operatorClickListener.onClick(buttonLabel)
      '.' -> decimalPointClickListener.onClick()
      '=' -> resultClickListener.onClick()
      else -> throw Exception("No click listener for button label $buttonLabel")
    }
  }
}
