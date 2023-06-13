package com.clickandvisit.data.repository.abs

import androidx.annotation.WorkerThread
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.global.enumeration.Optional


interface UserRepository {

    @WorkerThread
    suspend fun isLoggedInWithDelay(time: Long): Optional<User>

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

    @WorkerThread
    suspend fun signInAndCache(email: String, password: String): SignupResponse

    @WorkerThread
    suspend fun activateAccount(code: String): SignupResponse

    @WorkerThread
    suspend fun getUser(id: Int): UserResponse

    @WorkerThread
    suspend fun userUpdate(user: User): UserResponse

    @WorkerThread
    suspend fun sendActivationCode(userId: Int): SignupResponse

    @WorkerThread
    suspend fun activateAccount(req: ActivateAccountRequest): SignupResponse

    suspend fun reportUser(reportUserRequest: ReportUserRequest): ReportUserResponse

    suspend fun setPushToken(pushTokenRequest: PushTokenRequest): TokenResponse

}
