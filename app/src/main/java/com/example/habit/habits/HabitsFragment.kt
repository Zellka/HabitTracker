package com.example.habit.habits

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.habit.R
import com.example.habit.adapter.HabitAdapter
import com.example.habit.data.model.Habit
import com.example.habit.databinding.FragmentHabitsBinding

class HabitFragment : Fragment() {
    
    private lateinit var habitsViewModel: HabitsViewModel

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HabitAdapter
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            position = it.getInt(POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        habitsViewModel =
            ViewModelProvider(this)[HabitsViewModel::class.java]
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            Habit(
                0xFF1F51B5,
                2,
                122,
                "Много пить воды",
                arrayOf(1, 2),
                7,
                0,
                "Вода",
                0,
                "1"
            ),
            Habit(
                0xFF3F71B5,
                2,
                122,
                "Много пить воды",
                arrayOf(1, 2),
                7,
                0,
                "Вода",
                0,
                "1"
            ),
            Habit(
                0xFF5F51B5,
                2,
                122,
                "Не курить",
                arrayOf(1, 2),
                7,
                2,
                "Не курить",
                1,
                "1"
            ),
            Habit(
                0xFF3F51B8,
                2,
                122,
                "Много пить воды",
                arrayOf(1, 2),
                7,
                0,
                "Вода",
                0,
                "1"
            ),
            Habit(
                0xFF3F51B5,
                2,
                122,
                "Не курить",
                arrayOf(1, 2),
                7,
                1,
                "Не курить",
                1,
                "1"
            )
        )
        binding.rvHabits.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = HabitAdapter()
        adapter.setData(list.filter { it.type == position })
        binding.rvHabits.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_sort) {
            val filterFragment = FilterFragment()
            filterFragment.show(this.childFragmentManager, "Dialog")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) =
            HabitFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
    }
}