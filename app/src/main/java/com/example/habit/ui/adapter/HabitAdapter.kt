package com.example.habit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.data.model.Habit
import com.example.habit.databinding.ItemHabitBinding

class HabitAdapter(private var listener: (Habit) -> Unit) :
    RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    private var habits = mutableListOf<Habit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitAdapter.HabitHolder {
        val binding =
            ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = HabitHolder(binding)
        viewHolder.itemView.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION)
                listener(habits[viewHolder.adapterPosition])
        }
        return viewHolder
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
            habit.color?.let { binding.icHabit.setBackgroundColor(it.toInt()) }
        }
    }

    fun setData(newData: List<Habit>) {
        habits.clear()
        habits.addAll(newData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        habits.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, habits.size)
    }
}