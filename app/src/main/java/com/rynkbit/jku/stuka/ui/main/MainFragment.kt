package com.rynkbit.jku.stuka.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rynkbit.jku.stuka.R
import com.rynkbit.jku.stuka.identity.StudentStore

class MainFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        view.apply {
            findViewById<FloatingActionButton>(R.id.fabEditData)
                .setOnClickListener {
                    findNavController().navigate(R.id.action_mainFragment_to_editDataFragment)
                }

            val txtFirstname = findViewById<TextView>(R.id.txtFirstname)
            val txtLastname = findViewById<TextView>(R.id.txtLastname)
            val txtBirthdate = findViewById<TextView>(R.id.txtBirthdate)
            val txtMatriculationNumber = findViewById<TextView>(R.id.txtMatriculationNumber)
            val txtStudyCode = findViewById<TextView>(R.id.txtStudyCode)

            viewModel.student.observe(this@MainFragment.viewLifecycleOwner) {
                txtFirstname.text = it.firstname
                txtLastname.text = it.lastname
                txtBirthdate.text = it.birthdate
                txtMatriculationNumber.text = it.matriculationNumber
                txtStudyCode.text = it.studyCode
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.student.postValue(StudentStore(requireContext()).get())
    }
}