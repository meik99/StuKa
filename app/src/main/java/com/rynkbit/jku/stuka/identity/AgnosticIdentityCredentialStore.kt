package com.rynkbit.jku.stuka.identity

import android.content.Context
import androidx.security.identity.IdentityCredentialStore

class AgnosticIdentityCredentialStore(context: Context) {
    var isHardwareStore = false
    var credentialStore: IdentityCredentialStore

    init {
        val hardwareCredentialStore = IdentityCredentialStore.getHardwareInstance(context)

        if (hardwareCredentialStore != null) {
            credentialStore = hardwareCredentialStore
            isHardwareStore = true
        } else {
            credentialStore = IdentityCredentialStore.getSoftwareInstance(context)
        }
    }
}