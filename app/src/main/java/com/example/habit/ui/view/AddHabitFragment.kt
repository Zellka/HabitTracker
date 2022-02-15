package com.example.habit.ui.view

import android.graphics.Color
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
import com.example.habit.data.db.DatabaseBuilder
import com.example.habit.data.db.DatabaseHelperImpl
import com.example.habit.data.model.Habit
import com.example.habit.data.model.HabitPut
import com.example.habit.data.model.Priority
import com.example.habit.data.model.Type
import com.example.habit.ui.viewmodel.AddHabitViewModel
import com.example.habit.databinding.FragmentAddHabitBinding
import com.example.habit.ui.view.HabitsFragment.Companion.HABIT
import com.example.habit.ui.viewmodel.AddHabitViewModelFactory
import com.example.habit.data.model.Status
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver
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
            habit = it.getParcelable(HABIT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(
                this,
                AddHabitViewModelFactory(
                    ApiHelper(RetrofitBuilder.apiService), DatabaseHelperImpl(
                        DatabaseBuilder.getInstance(this.requireContext())
                    )
                )
            )[AddHabitViewModel::class.java]
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uidHabit: String? = null
        var habitColor = Color.RED
        if (habit != null) {
            uidHabit = habit?.uid
            habitColor = habit?.color!!
            binding.editName.setText(habit?.title)
            binding.editDescription.setText(habit?.description)
            binding.editCount.setText(habit?.count.toString())
            binding.editFrequency.setText(habit?.frequency.toString())
            habit?.priority?.let { binding.menuPriority.setSelection(it) }
            if (habit?.type == Type.GOOD.value)
                binding.typeGood.isChecked = true
            else
                binding.typeBad.isChecked = true

        }
        binding.btnColor.setOnClickListener {
            ColorPickerPopup.Builder(this.requireContext())
                .initialColor(habitColor)
                .enableBrightness(true)
                .enableAlpha(true)
                .okTitle(resources.getString(R.string.btn_choose))
                .cancelTitle(resources.getString(R.string.btn_cancel))
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(object : ColorPickerObserver() {
                    override fun onColorPicked(color: Int) {
                        habitColor = color
                    }
                })
        }

        binding.btnOk.setOnClickListener {
            val habit = HabitPut(
                habitColor,
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
            "Низкий" -> Priority.LOW.value
            "Средний" -> Priority.MEDIUM.value
            "Высокий" -> Priority.HIGH.value
            else -> Priority.LOW.value
        }
    }

    private fun getType(): Int {
        val selectedType =
            requireView().findViewById<RadioButton>(binding.type.checkedRadioButtonId)
        return when (selectedType?.text.toString()) {
            resources.getString(R.string.type_good) -> Type.GOOD.value
            resources.getString(R.string.type_bad) -> Type.BAD.value
            else -> Type.GOOD.value
        }
    }

    private fun putHabit(habit: HabitPut) {
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