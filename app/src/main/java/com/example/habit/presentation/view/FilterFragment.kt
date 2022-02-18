package com.example.habit.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.habit.app.App
import com.example.habit.presentation.Sort
import com.example.habit.databinding.FragmentFilterBinding
import com.example.habit.presentation.factory.HabitViewModelFactory
import com.example.habit.presentation.viewmodels.HabitViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class FilterFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: HabitViewModelFactory

    private lateinit var habitsViewModel: HabitViewModel
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        habitsViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            HabitViewModel::class.java
        )
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            if (binding.search.text?.isNotEmpty() == true) {
                habitsViewModel.filter(binding.search.text.toString())
                setSortList()
            }
            setSortList()
            this.dismiss()
        }
        binding.btnClear.setOnClickListener {
            habitsViewModel.filter("")
            this.dismiss()
        }
    }

    private fun setSortList() {
        if (binding.sortName.isChecked) {
            habitsViewModel.sort(Sort.NAME)
        }
        if (binding.sortDate.isChecked) {
            habitsViewModel.sort(Sort.DATE)
        }
        if (binding.sortPriority.isChecked) {
            habitsViewModel.sort(Sort.PRIORITY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}