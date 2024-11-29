package com.example.addtaskfeature.addTask

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.addtaskfeature.R
import com.example.addtaskfeature.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskFragment : Fragment(R.layout.fragment_add_task) {

    private val binding by viewBinding(FragmentAddTaskBinding::bind)
    private val viewModel: AddTaskViewModel by viewModel()
    private var imageString: String? = null
    private val imageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.changeImage.setImageURI(uri)
                imageString = uri.toString()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        viewModel.viewModelScope.launch {
            viewModel.insertMessageFlow.collectLatest {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.changeImage.setOnClickListener {
            imageLauncher.launch("image/*")
        }
        binding.btnAddTask.setOnClickListener {
            val task = binding.tvTask.text.toString()
            val date = binding.tvDate.text.toString()

            val taskUI = TaskUI(0, task, date, imageString.toString())
            viewModel.insertTask(taskUI)

            requireActivity().finish()
        }
    }
}