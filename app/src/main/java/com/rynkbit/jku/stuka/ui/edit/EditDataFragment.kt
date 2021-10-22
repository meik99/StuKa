package com.rynkbit.jku.stuka.ui.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rynkbit.jku.stuka.R
import com.rynkbit.jku.stuka.identity.StudentStore
import java.lang.UnsupportedOperationException

class EditDataFragment : Fragment() {
    private lateinit var viewModel: EditDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_data_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditDataViewModel::class.java)

        val context = requireContext()
        val studentProfile = StudentStore(context).get()

        viewModel.applyStudentProfile(studentProfile)

        view.apply {
            findViewById<FloatingActionButton>(R.id.fabSaveData)
                .setOnClickListener {
                    saveData()
                    findNavController().navigate(R.id.action_editDataFragment_to_mainFragment)
                }

            val editFirstname = findViewById<EditText>(R.id.editFirstname)
            val editLastname = findViewById<EditText>(R.id.editLastname)
            val editBirthdate = findViewById<EditText>(R.id.editBirthdate)
            val editMatriculationNumber = findViewById<EditText>(R.id.editMatriculationNumber)
            val editStudyCode = findViewById<EditText>(R.id.editStudyCode)

            editFirstname.setTextIfNotEmpty(studentProfile.firstname)
            editLastname.setTextIfNotEmpty(studentProfile.lastname)
            editBirthdate.setTextIfNotEmpty(studentProfile.birthdate)
            editMatriculationNumber.setTextIfNotEmpty(studentProfile.matriculationNumber)
            editStudyCode.setTextIfNotEmpty(studentProfile.studyCode)

            editFirstname.addTextChangedListener {
                viewModel.firstname = it.toString()
            }
            editLastname.addTextChangedListener {
                viewModel.lastname = it.toString()
            }
            editBirthdate.addTextChangedListener {
                viewModel.birthdate = it.toString()
            }
            editMatriculationNumber.addTextChangedListener {
                viewModel.matriculationNumber = it.toString()
            }
            editStudyCode.addTextChangedListener {
                viewModel.studyCode = it.toString()
            }
        }
    }

    private fun EditText.setTextIfNotEmpty(value: String) {
        if (value.isNotEmpty()) {
            setText(value)
        }
    }

    private fun saveData() {
        val context = requireContext()
        try {
            StudentStore(context)
                .store(viewModel.studentProfile)
        } catch (e: UnsupportedOperationException) {
            Snackbar
                .make(requireView(), R.string.updating_credentials_not_supported, Snackbar.LENGTH_LONG)
                .show()
        }
    }

}