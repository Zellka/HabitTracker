package com.example.habit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.api.RetrofitBuilder
import com.example.habit.data.model.Sort
import com.example.habit.databinding.FragmentFilterBinding
import com.example.habit.ui.viewmodel.HabitsViewModel
import com.example.habit.ui.viewmodel.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterFragment : BottomSheetDialogFragment() {

    private lateinit var habitsViewModel: HabitsViewModel
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        habitsViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
            )[HabitsViewModel::class.java]
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            if (binding.search.text?.isNotEmpty() == true) {
                habitsViewModel.setFilterHabitList(binding.search.text.toString())
                setSortList()
            }
            setSortList()
            this.dismiss()
        }
        binding.btnClear.setOnClickListener {
            habitsViewModel.setNoFilter()
            this.dismiss()
        }
    }

    private fun setSortList() {
        if (binding.sortName.isChecked) {
            habitsViewModel.setSortHabitList(Sort.NAME)
        }
        if (binding.sortDate.isChecked) {
            habitsViewModel.setSortHabitList(Sort.DATE)
        }
        if (binding.sortPriority.isChecked) {
            habitsViewModel.setSortHabitList(Sort.PRIORITY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}