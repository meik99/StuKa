package com.rynkbit.jku.stuka.identity

import androidx.security.identity.AccessControlProfile
import androidx.security.identity.AccessControlProfileId

class AccessControlProfile {
    val accessControlProfileId = AccessControlProfileId(1)
    val accessControlProfile = AccessControlProfile.Builder(accessControlProfileId)
        .setUserAuthenticationRequired(false)
        .build()
}