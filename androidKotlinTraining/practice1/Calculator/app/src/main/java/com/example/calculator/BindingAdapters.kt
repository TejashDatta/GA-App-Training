package com.example.calculator

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("operand1", "operand2", "operator", "result")
fun TextView.displayExpression(operand1: String?, operand2: String?, operator: Char?, result: Float?) {
  val safeOperand1 = operand1 ?: ""
  val safeOperand2 = operand2 ?: ""
  val safeOperator = operator ?: ""
  val formattedResult = if (result != null) "= $result" else ""

  text = "$safeOperand1 $safeOperator $safeOperand2 $formattedResult"
}
