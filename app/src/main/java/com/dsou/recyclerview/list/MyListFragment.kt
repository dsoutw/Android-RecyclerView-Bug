package com.dsou.recyclerview.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsou.recyclerview.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding get() = _binding!!

    private var appliedRuleListCount: Int = 0
    private val appliedRuleList: MutableStateFlow<List<ItemData>> = MutableStateFlow(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = initBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appliedRuleListAdapter = MyListAdapter {
            binding.clickedItemText.text = it.text
        }
        binding.ruleList.adapter = appliedRuleListAdapter
        binding.ruleList.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            appliedRuleList.collect { list ->
                withContext(Dispatchers.Main) {
                    appliedRuleListAdapter.submitList(list)
                }
            }
        }

        binding.addButton.setOnClickListener {
            onAddClicked()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBinding {
        return FragmentListBinding.inflate(inflater, container, false)
    }

    private fun onAddClicked() {
        appliedRuleListCount++
        val list = (1..appliedRuleListCount).map {
            ItemData(id = it)
        }
        appliedRuleList.value = list
    }
}