package com.example.habit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.data.model.Habit
import com.example.habit.databinding.ItemHabitBinding

class HabitAdapter :
    RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    private val habits = mutableListOf<Habit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitAdapter.HabitHolder {
        val binding =
            ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitAdapter.HabitHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount() = habits.size

    inner class HabitHolder(var binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.titleHabit.text = habit.title
            binding.descriptionHabit.text = habit.description
            binding.icHabit.setBackgroundColor(habit.color.toInt())
        }
    }

    fun setData(newData: List<Habit>) {
        habits.clear()
        habits.addAll(newData)
        notifyDataSetChanged()
    }
}