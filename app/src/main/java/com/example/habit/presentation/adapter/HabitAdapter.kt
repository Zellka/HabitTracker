package com.example.habit.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Habit
import com.example.habit.databinding.ItemHabitBinding
import com.example.habit.presentation.Sort

class HabitAdapter(private var listener: (Habit) -> Unit) :
    RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    private var habits = mutableListOf<Habit>()
    private var filterHabits = mutableListOf<Habit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val binding =
            ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = HabitHolder(binding)
        viewHolder.itemView.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION)
                listener(habits[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount() = habits.size

    inner class HabitHolder(var binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.titleHabit.text = habit.title
            binding.descriptionHabit.text = habit.description
            binding.icHabit.borderColor = habit.color!!
        }
    }

    fun filterList(filter: String) {
        val filterList = habits.filter { it.title == filter }
        habits.clear()
        habits.addAll(filterList)
        notifyDataSetChanged()
    }

    fun sortList(sort: Sort) {
        val filterList = when (sort) {
            Sort.DATE -> {
                habits.sortedBy { it.date }
            }
            Sort.NAME -> {
                habits.sortedBy { it.title }
            }
            Sort.PRIORITY -> {
                habits.sortedBy { it.priority }
            }
        }
        habits.clear()
        habits.addAll(filterList)
        notifyDataSetChanged()
    }

    fun noFilter() {
        habits.clear()
        habits.addAll(filterHabits)
        notifyDataSetChanged()
    }

    fun setData(newData: List<Habit>) {
        habits.clear()
        filterHabits.clear()
        habits.addAll(newData)
        filterHabits.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        habits.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, habits.size)
    }
}