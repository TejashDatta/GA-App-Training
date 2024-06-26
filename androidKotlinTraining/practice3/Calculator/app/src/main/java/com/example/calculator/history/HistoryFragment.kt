package com.example.calculator.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.calculator.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
  lateinit var binding: FragmentHistoryBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    val historyManager = HistoryManager(requireActivity().getPreferences(Context.MODE_PRIVATE))

    binding = FragmentHistoryBinding.inflate(inflater)
    binding.lifecycleOwner = viewLifecycleOwner

    with(binding.historyList) {
      adapter = HistoryListAdapter(historyManager.items) { history -> onHistorySelected(history) }
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    return binding.root
  }

  private fun onHistorySelected(history: String) {
    findNavController().navigate(
      HistoryFragmentDirections.actionHistoryFragmentToCalculatorFragment(history))
  }
}
