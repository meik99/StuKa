package com.rynkbit.jku.stuka.identity

import android.content.Context
import androidx.security.identity.IdentityCredential
import androidx.security.identity.IdentityCredentialStore
import androidx.security.identity.ResultData
import java.lang.UnsupportedOperationException

const val CREDENTIAL_NAME = "STUDENT"
const val DOC_TYPE = "CARD"
const val IDENTITY_FEATURE = "android.hardware.identity_credential"

class StudentStore(
    context: Context,
    private val agnosticIdentityCredentialStore: AgnosticIdentityCredentialStore = AgnosticIdentityCredentialStore(
        context
    )
) {

    init {

    }

    fun store(student: StudentProfile) {
        val credentialStore = agnosticIdentityCredentialStore.credentialStore
        val credentials = credentialStore.getCredentialByName(
            CREDENTIAL_NAME,
            IdentityCredentialStore.CIPHERSUITE_ECDHE_HKDF_ECDSA_WITH_AES_256_GCM_SHA256
        )

        if (credentials == null) {
            createStudent(student)
        } else {
            updateStudent(student, credentials)
        }
    }

    fun get(): StudentProfile {
        val credentialStore = agnosticIdentityCredentialStore.credentialStore
        val credentials = credentialStore.getCredentialByName(
            CREDENTIAL_NAME,
            IdentityCredentialStore.CIPHERSUITE_ECDHE_HKDF_ECDSA_WITH_AES_256_GCM_SHA256
        )

        if (credentials != null) {
            val results = credentials.getEntries(
                null,
                mapOf(
                    Pair(
                        CREDENTIAL_NAMESPACE, listOf(
                            CREDENTIAL_STUDENT_FIRSTNAME,
                            CREDENTIAL_STUDENT_LASTNAME,
                            CREDENTIAL_STUDENT_BIRTHDATE,
                            CREDENTIAL_STUDENT_MATRICULATION_NUMBER,
                            CREDENTIAL_STUDENT_STUDY_CODE
                        )
                    )
                ),
                null
            )
            return results.toStudent()
        }

        return StudentProfile()
    }

    private fun createStudent(student: StudentProfile) {
        val credentialStore = agnosticIdentityCredentialStore.credentialStore
        val credentials = credentialStore.createCredential(CREDENTIAL_NAME, DOC_TYPE)
        credentials.personalize(student.toPersonalizationData())
    }

    private fun updateStudent(student: StudentProfile, credentials: IdentityCredential) {
        val credentialStore = agnosticIdentityCredentialStore.credentialStore

        when {
            credentialStore.capabilities.isUpdateSupported -> {
                credentials.update(student.toPersonalizationData())
            }
            credentialStore.capabilities.isDeleteSupported -> {
                deleteStudent(credentials)
                createStudent(student)
            }
            else -> {
                throw UnsupportedOperationException()
            }
        }
    }

    private fun deleteStudent(credentials: IdentityCredential) {
        credentials.delete(ByteArray(1))
    }

    private fun ResultData.toStudent(): StudentProfile = StudentProfile(
        getEntryString(CREDENTIAL_NAMESPACE, CREDENTIAL_STUDENT_FIRSTNAME) ?: "",
        getEntryString(CREDENTIAL_NAMESPACE, CREDENTIAL_STUDENT_LASTNAME) ?: "",
        getEntryString(CREDENTIAL_NAMESPACE, CREDENTIAL_STUDENT_BIRTHDATE) ?: "",
        getEntryString(CREDENTIAL_NAMESPACE, CREDENTIAL_STUDENT_MATRICULATION_NUMBER) ?: "",
        getEntryString(CREDENTIAL_NAMESPACE, CREDENTIAL_STUDENT_STUDY_CODE) ?: ""
    )
}
