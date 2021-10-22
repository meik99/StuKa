package com.rynkbit.jku.stuka.ui.edit

import androidx.lifecycle.ViewModel
import com.rynkbit.jku.stuka.identity.StudentProfile

class EditDataViewModel : ViewModel() {
    var firstname: String = ""
    var lastname: String = ""
    var birthdate: String = ""
    var matriculationNumber: String = ""
    var studyCode: String = ""

    val studentProfile
        get() = StudentProfile(
            firstname, lastname, birthdate, matriculationNumber, studyCode
        )

    fun applyStudentProfile(studentProfile: StudentProfile) {
        firstname = studentProfile.firstname
        lastname = studentProfile.lastname
        birthdate = studentProfile.birthdate
        matriculationNumber = studentProfile.matriculationNumber
        studyCode = studentProfile.studyCode
    }
}