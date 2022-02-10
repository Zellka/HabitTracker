package com.example.habit.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.habit.R
import com.example.habit.data.api.ApiHelper
import com.example.habit.data.api.RetrofitBuilder
import com.example.habit.data.model.Habit
import com.example.habit.ui.viewmodel.AddHabitViewModel
import com.example.habit.databinding.FragmentAddHabitBinding
import com.example.habit.ui.viewmodel.AddHabitViewModelFactory
import com.example.habit.ui.viewmodel.HabitsViewModel
import com.example.habit.ui.viewmodel.ViewModelFactory
import com.example.habit.utils.Status
import java.util.*

class AddHabitFragment : Fragment() {

    private lateinit var viewModel: AddHabitViewModel

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!

    private var habit: Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            habit = it.getParcelable("HABIT")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(
                this,
                AddHabitViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
            )[AddHabitViewModel::class.java]
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uidHabit: String? = null
        if (habit != null) {
            uidHabit = habit?.uid
            binding.editName.setText(habit?.title)
            binding.editDescription.setText(habit?.description)
            binding.editCount.setText(habit?.count.toString())
            binding.editFrequency.setText(habit?.frequency.toString())
            habit?.priority?.let { binding.menuPriority.setSelection(it) }
            if (habit?.type == 0)
                binding.typeGood.isChecked = true
            else
                binding.typeBad.isChecked = true

        }

        binding.btnOk.setOnClickListener {
            val habit = Habit(
                0xFF1F51B5,
                binding.editCount.text.toString().toInt(),
                Calendar.getInstance().time.time,
                binding.editDescription.text.toString(),
                arrayOf(),
                binding.editFrequency.text.toString().toInt(),
                getPriority(),
                binding.editName.text.toString(),
                getType(),
                uidHabit
            )
            putHabit(habit)
        }
    }

    private fun getPriority(): Int {
        return when (binding.menuPriority.selectedItem.toString()) {
            "Низкий" -> 0
            "Средний" -> 1
            "Высокий" -> 2
            else -> 0
        }
    }

    private fun getType(): Int {
        val selectedType =
            requireView().findViewById<RadioButton>(binding.type.checkedRadioButtonId)
        return when (selectedType?.text.toString()) {
            resources.getString(R.string.type_good) -> 0
            resources.getString(R.string.type_bad) -> 1
            else -> 0
        }
    }

    private fun putHabit(habit: Habit) {
        viewModel.putHabit(habit)
            .observe(this.viewLifecycleOwner, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Toast.makeText(
                                this.requireContext(),
                                "Успех",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addHabitFragment_to_habitsFragment)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            findNavController().navigate(R.id.action_addHabitFragment_to_habitsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}