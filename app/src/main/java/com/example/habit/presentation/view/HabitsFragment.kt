package com.example.habit.presentation.view

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.R
import com.example.habit.presentation.adapter.HabitAdapter
import com.example.domain.models.Habit
import com.example.domain.models.HabitDone
import com.example.habit.app.App
import com.example.habit.databinding.FragmentHabitsBinding
import com.example.habit.presentation.factory.HabitViewModelFactory
import com.example.habit.presentation.viewmodels.HabitViewModel
import java.util.*
import javax.inject.Inject
import androidx.lifecycle.Observer

class HabitsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: HabitViewModelFactory

    private lateinit var habitsViewModel: HabitViewModel

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HabitAdapter
    private var position: Int? = null
    private var habitList: List<Habit> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
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
            ViewModelProvider(requireActivity(), viewModelFactory).get(HabitViewModel::class.java)
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getHabits()
        setFilter()
        habitsViewModel.isError.observe(this, {
            if (it) {
                Toast.makeText(this.requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        })
        habitsViewModel.isLoad.observe(this, {
            binding.progressBar.isVisible = it
        })
        habitsViewModel.toastDone.observe(this, {
            if (it.isNotEmpty()) {
                Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setRecyclerView() {
        binding.rvHabits.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = HabitAdapter { doneHabit(it) }
        adapter.setData(listOf())
        binding.rvHabits.adapter = adapter
        setupSwipe()
    }

    private fun setFilter() {
        habitsViewModel.filterText.observe(this, {
            if (it.isNotEmpty()) {
                adapter.filterList(it)
            } else {
                adapter.noFilter()
            }
        })
        habitsViewModel.sortText.observe(this, {
            if (it.name.isNotEmpty()) {
                adapter.sortList(it)
            } else {
                adapter.noFilter()
            }
        })
    }

    private fun getHabits() {
        habitsViewModel.getHabits().observe(this,
            { setHabits(it) })
    }

    private fun doneHabit(habit: Habit) {
        habitsViewModel.doneHabit(habit)
    }

    private fun setHabits(list: List<Habit>) {
        val sortList = list.filter { it.type == position }
        adapter.apply {
            setData(sortList)
            habitList = sortList
        }
    }

    private fun editHabit(habit: Habit) {
        val bundle = bundleOf(HABIT to habit)
        findNavController().navigate(
            R.id.action_habitsFragment_to_addHabitFragment,
            bundle
        )
    }

    private fun deleteHabit(position: Int) {
        if (view?.parent != null) {
            habitsViewModel.deleteHabit(habitList[position])
        }
        adapter.removeItem(position)
    }

    private fun setupSwipe() {
        val point = Paint()
        val simpleItemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT) {
                    deleteHabit(position)
                } else {
                    editHabit(habitList[position])
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                point.color = Color.WHITE
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        val background = RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, point)
                        icon = ContextCompat.getDrawable(
                            this@HabitsFragment.requireContext(),
                            R.drawable.ic_edit
                        )?.toBitmap()!!
                        c.drawBitmap(
                            icon, null, RectF(
                                itemView.left.toFloat() + width,
                                itemView.top.toFloat() + width,
                                itemView.left.toFloat() + 2 * width,
                                itemView.bottom.toFloat() - width
                            ), point
                        )
                    } else {
                        val background = RectF(
                            itemView.right.toFloat() + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat()
                        )
                        c.drawRect(background, point)
                        icon =
                            ContextCompat.getDrawable(
                                this@HabitsFragment.requireContext(),
                                R.drawable.ic_delete
                            )?.toBitmap()!!
                        c.drawBitmap(
                            icon, null, RectF(
                                itemView.right.toFloat() - 2 * width,
                                itemView.top.toFloat() + width,
                                itemView.right.toFloat() - width,
                                itemView.bottom.toFloat() - width
                            ), point
                        )
                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvHabits)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_sort) {
            val filterFragment = FilterFragment()
            filterFragment.show(this.childFragmentManager, DIALOG_TAG)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val POSITION = "position"
        const val HABIT = "HABIT"
        const val DIALOG_TAG = "Dialog"

        @JvmStatic
        fun newInstance(position: Int) =
            HabitsFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
    }
}