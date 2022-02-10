package com.example.habit.ui.view

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.R
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.api.RetrofitBuilder
import com.example.habit.ui.adapter.HabitAdapter
import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitDone
import com.example.habit.data.model.HabitUID
import com.example.habit.databinding.FragmentHabitsBinding
import com.example.habit.ui.viewmodel.HabitsViewModel
import com.example.habit.ui.viewmodel.ViewModelFactory
import com.example.habit.utils.Status
import kotlinx.android.synthetic.main.fragment_filter.*
import java.time.LocalDateTime
import java.util.*

class HabitsFragment : Fragment() {

    private lateinit var habitsViewModel: HabitsViewModel

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: HabitAdapter
    private var position: Int? = null
    private var habitList: List<Habit> = mutableListOf()

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
            ViewModelProvider(
                this,
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
            )[HabitsViewModel::class.java]
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHabits.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = HabitAdapter { doneHabit(it) }
        adapter.setData(listOf())
        binding.rvHabits.adapter = adapter
        setupSwipe()
        getHabits()
    }

    private fun getHabits() {
        habitsViewModel.getHabits()
            .observe(this.viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.rvHabits.visibility = View.VISIBLE
                            //binding.progressBar.visibility = View.GONE
                            resource.data?.let { habits -> setHabits(habits) }
                        }
                        Status.ERROR -> {
                            //binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this.requireContext(),
                                resource.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        Status.LOADING -> {
                            //binding.progressBar.visibility = View.VISIBLE
                            binding.rvHabits.visibility = View.GONE
                        }
                    }
                }
            })
    }

    private fun doneHabit(habit: Habit) {
        habit.uid?.let { HabitDone(Calendar.getInstance().time.time, it) }?.let { it ->
            habitsViewModel.postHabitDone(it)
                .observe(this.viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    this.requireContext(),
                                    "Сделано",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            Status.ERROR -> {
                                Toast.makeText(
                                    this.requireContext(),
                                    resource.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            Status.LOADING -> {
                            }
                        }
                    }
                })
        }
    }

    private fun deleteHabit(habit: HabitUID) {
        if (view?.parent != null) {
            habitsViewModel.deleteHabit(habit)
                .observe(this.viewLifecycleOwner, Observer {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                Toast.makeText(
                                    this.requireContext(),
                                    "Удалено",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            Status.ERROR -> {
                                //binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    this.requireContext(),
                                    resource.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            Status.LOADING -> {
                                //binding.progressBar.visibility = View.VISIBLE
                            }
                        }
                    }
                })
        }
    }

    private fun setHabits(list: List<Habit>) {
        val sortList = list.filter { it.type == position }
        adapter.apply {
            setData(sortList)
            habitList = sortList
            notifyDataSetChanged()
        }
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
                    habitList[position].uid?.let { HabitUID(it) }?.let { deleteHabit(it) }
                    adapter.removeItem(position)
                } else {
                    val bundle = bundleOf("HABIT" to habitList[position])
                    findNavController().navigate(
                        R.id.action_habitsFragment_to_addHabitFragment,
                        bundle
                    )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) =
            HabitsFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
    }
}