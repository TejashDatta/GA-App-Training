package com.example.calculator.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R

class ButtonGridAdapter(
  private val operandClickListener: (Char) -> Unit,
  private val operatorClickListener: (Char) -> Unit,
  private val decimalPointClickListener: () -> Unit,
  private val resultClickListener: () -> Unit
): RecyclerView.Adapter<ButtonGridAdapter.ViewHolder>() {

  class ViewHolder private constructor(val buttonView: Button): RecyclerView.ViewHolder(buttonView) {
    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val buttonView = layoutInflater.inflate(R.layout.grid_view_button, parent, false) as Button
        return ViewHolder(buttonView)
      }
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
    return ViewHolder.from(parent)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
    val buttonLabel = buttonLabels[position]
    viewHolder.buttonView.text = buttonLabel.toString()
    viewHolder.buttonView.setOnClickListener { onClickButton(buttonLabel) }
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
