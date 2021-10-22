package com.rynkbit.jku.stuka.ui.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rynkbit.jku.stuka.R

const val CREDENTIAL_NAME = "STUDENT_6"
const val DOC_TYPE = "CARD"
const val CREDENTIAL_NAMESPACE = "StuKa"
const val STUDENT_NAME = "NAME"
const val IDENTITY_FEATURE = "android.hardware.identity_credential"

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

        view.apply {
            findViewById<FloatingActionButton>(R.id.fabSaveData)
                .setOnClickListener {
                    findNavController().navigate(R.id.action_editDataFragment_to_mainFragment)
                }
        }
    }
}