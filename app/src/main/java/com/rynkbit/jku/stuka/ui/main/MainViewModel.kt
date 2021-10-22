package com.rynkbit.jku.stuka.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rynkbit.jku.stuka.identity.StudentProfile

class MainViewModel : ViewModel() {
    var student = MutableLiveData<StudentProfile>()
}