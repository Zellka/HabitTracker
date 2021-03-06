package com.example.habit.presentation.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habit.R
import com.example.habit.databinding.FragmentAllHabitsBinding
import com.example.habit.presentation.adapter.PagerAdapter

class AllHabitsFragment : Fragment() {

    private var _binding: FragmentAllHabitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = PagerAdapter(this.requireContext(), childFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.btnAddHabit.setOnClickListener {
            findNavController().navigate(R.id.action_habitsFragment_to_addHabitFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}