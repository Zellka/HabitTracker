package com.example.habit.presentation.view

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.habit.R
import com.example.domain.models.Habit
import com.example.domain.models.HabitPut
import com.example.habit.app.App
import com.example.habit.presentation.Priority
import com.example.habit.presentation.Type
import com.example.habit.presentation.viewmodels.AddHabitViewModel
import com.example.habit.databinding.FragmentAddHabitBinding
import com.example.habit.presentation.factory.AddHabitViewModelFactory
import com.example.habit.presentation.view.HabitsFragment.Companion.HABIT
import top.defaults.colorpicker.ColorPickerPopup
import top.defaults.colorpicker.ColorPickerPopup.ColorPickerObserver
import java.util.*
import javax.inject.Inject

class AddHabitFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AddHabitViewModelFactory

    private lateinit var viewModel: AddHabitViewModel

    private var _binding: FragmentAddHabitBinding? = null
    private val binding get() = _binding!!

    private var habit: Habit? = null
    private var habitColor = Color.RED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        setHasOptionsMenu(true)
        arguments?.let {
            habit = it.getSerializable(HABIT) as Habit?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            AddHabitViewModel::class.java
        )
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isError.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(this.requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.requireContext(), "Успех", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addHabitFragment_to_habitsFragment)
            }
        })
        var uidHabit: String? = null
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
        binding.btnOk.setOnClickListener {
            val habit = HabitPut(
                habitColor,
                binding.editCount.text.toString().toInt(),
                (Date().time / 1000),
                binding.editDescription.text.toString(),
                arrayOf(),
                binding.editFrequency.text.toString().toInt(),
                getPriority(),
                binding.editName.text.toString(),
                getType(),
                uidHabit
            )
            viewModel.addEditHabit(habit)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_color, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.home) {
            findNavController().navigate(R.id.action_addHabitFragment_to_habitsFragment)
        }
        if (id == R.id.action_color) {
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
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}