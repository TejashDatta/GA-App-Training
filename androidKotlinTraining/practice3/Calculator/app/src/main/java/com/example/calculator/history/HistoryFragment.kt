package com.example.calculator.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.calculator.CalculatorViewModel
import com.example.calculator.R
import com.example.calculator.calculator.ButtonGridAdapter
import com.example.calculator.calculator.CalculatorFragment
import com.example.calculator.calculator.CalculatorViewModelFactory
import com.example.calculator.calculator.GridMarginItemDecoration
import com.example.calculator.databinding.FragmentCalculatorBinding
import com.example.calculator.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
  lateinit var binding: FragmentHistoryBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentHistoryBinding.inflate(inflater)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding.root
  }
}
