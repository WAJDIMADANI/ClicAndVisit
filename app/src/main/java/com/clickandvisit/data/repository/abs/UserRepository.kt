package com.clickandvisit.data.repository.abs

import androidx.annotation.WorkerThread
import com.clickandvisit.data.model.user.ActivateAccountRequest
import com.clickandvisit.data.model.user.User
import com.clickandvisit.data.model.user.signup.SignupRequest
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.global.enumeration.Optional
import com.squareup.moshi.Json

interface UserRepository {

    @WorkerThread
    suspend fun isLoggedInWithDelay(time: Long): Optional<User>

    @WorkerThread
    suspend fun signInAndCache(email: String, password: String): SignupResponse

    @WorkerThread
    suspend fun activateAccount(code: String): SignupResponse

    @WorkerThread
    suspend fun signUp(
        proPar: Int,
        siret: String,
        rSocial: String,
        civility: Int,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): SignupResponse

}
