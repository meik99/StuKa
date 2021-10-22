package com.rynkbit.jku.stuka.identity

import androidx.security.identity.PersonalizationData

const val CREDENTIAL_NAMESPACE = "STUKA"
const val CREDENTIAL_STUDENT_FIRSTNAME = "FIRSTNAME"
const val CREDENTIAL_STUDENT_LASTNAME = "LASTNAME"
const val CREDENTIAL_STUDENT_BIRTHDATE = "BIRTHDATE"
const val CREDENTIAL_STUDENT_MATRICULATION_NUMBER = "MATRICULATION_NUMBER"
const val CREDENTIAL_STUDENT_STUDY_CODE = "STUDY_CODE"

class StudentProfile(
    val firstname: String = "",
    val lastname: String = "",
    val birthdate: String = "",
    val matriculationNumber: String = "",
    val studyCode: String = "",
    val studentAccessControlProfile: AccessControlProfile = AccessControlProfile(),
) {
    fun toPersonalizationData() =
        PersonalizationData.Builder()
            .addAccessControlProfile(studentAccessControlProfile.accessControlProfile)
            .putEntry(CREDENTIAL_STUDENT_FIRSTNAME, firstname)
            .putEntry(CREDENTIAL_STUDENT_LASTNAME, lastname)
            .putEntry(CREDENTIAL_STUDENT_BIRTHDATE, birthdate)
            .putEntry(CREDENTIAL_STUDENT_MATRICULATION_NUMBER, matriculationNumber)
            .putEntry(CREDENTIAL_STUDENT_STUDY_CODE, studyCode)
            .build()

    private fun PersonalizationData.Builder.putEntry(
        key: String,
        value: String
    ): PersonalizationData.Builder =
        this.putEntryString(
            CREDENTIAL_NAMESPACE,
            key,
            listOf(studentAccessControlProfile.accessControlProfileId),
            value
        )
}