package com.rynkbit.jku.stuka.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.security.identity.*
import com.google.android.material.snackbar.Snackbar
import com.rynkbit.jku.stuka.AgnosticIdentityCredentialStore
import com.rynkbit.jku.stuka.R
import java.lang.Exception

const val CREDENTIAL_NAME = "STUDENT_6"
const val DOC_TYPE = "CARD"
const val CREDENTIAL_NAMESPACE = "StuKa"
const val STUDENT_NAME = "NAME"
const val IDENTITY_FEATURE = "android.hardware.identity_credential"

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

        val context = requireContext()

        // All of the code below was made by trial and error
        // The documentation only gives minimal insight and the error messages are close to useless
        val identityStore = AgnosticIdentityCredentialStore(context)
        val credentials = identityStore.credentialStore.createCredential(CREDENTIAL_NAME, DOC_TYPE)

        // For some reason an access control profile is needed, however
        // I could not find the reason why or what that does
        val accessControlProfileId = AccessControlProfileId(1)
        val accessControlProfile = AccessControlProfile.Builder(accessControlProfileId)
            .setUserAuthenticationRequired(false)
            .build()

        val data = PersonalizationData.Builder()
            .addAccessControlProfile(accessControlProfile)
            .putEntryString(
                CREDENTIAL_NAMESPACE,
                STUDENT_NAME,
                listOf(accessControlProfileId),
                "Test Name"
            )
            .build()

        try {
            credentials.personalize(data)
            val readCredentials = identityStore.credentialStore.getCredentialByName(
                CREDENTIAL_NAME,
                IdentityCredentialStore.CIPHERSUITE_ECDHE_HKDF_ECDSA_WITH_AES_256_GCM_SHA256
            )

            // If no access control profile is created or used
            // the result data contains all keys, but a null value will be returned
            val resultData = readCredentials?.getEntries(
                null,
                mapOf(Pair(CREDENTIAL_NAMESPACE, listOf(STUDENT_NAME))),
                null
            )
            val status = resultData?.getStatus(CREDENTIAL_NAMESPACE, STUDENT_NAME)
            val name = resultData?.getEntryString(CREDENTIAL_NAMESPACE, STUDENT_NAME)
            Snackbar.make(view, "Somehow this worked: $status: $name", Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(MainFragment::class.java.simpleName, e.message, e)
        }
    }
}