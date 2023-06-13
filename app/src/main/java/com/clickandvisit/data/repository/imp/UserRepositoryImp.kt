package com.clickandvisit.data.repository.imp

import androidx.annotation.WorkerThread
import com.clickandvisit.base.BaseRepository
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.data.retrofit.APIClient
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.SharedPreferences
import kotlinx.coroutines.delay
import javax.inject.Inject


class UserRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences
) :
    BaseRepository(apiClient, sharedPreferences), UserRepository {


    @WorkerThread
    override suspend fun isLoggedInWithDelay(time: Long): Optional<User> {
        delay(time)
        return if (sharedPreferences.isConnected()) {
            Optional.Some(sharedPreferences.getUser())
        } else {
            Optional.None
        }
    }

    override suspend fun signUp(
        proPar: Int,
        siret: String,
        rSocial: String,
        civility: Int,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): SignupResponse {
        return apiClient.signUp(
            proPar,
            siret,
            rSocial,
            civility,
            firstName,
            lastName,
            email,
            password,
            phoneNumber
        )
    }

    @WorkerThread
    override suspend fun signInAndCache(email: String, password: String): SignupResponse {
        val response = apiClient.signIn(Login(email, password))
        sharedPreferences.saveUser(response.user)
        return response
    }

    override suspend fun activateAccount(code: String): SignupResponse {
        val response = apiClient.activateAccount(
            ActivateAccountRequest(
                sharedPreferences.getUser().id.toInt(),
                code
            )
        )
        //FIXME:sharedPreferences.saveUser(response.user)
        return response
    }

    override suspend fun getUser(id: Int): UserResponse {
        return apiClient.getUser(id)
    }

    override suspend fun userUpdate(user: User): UserResponse {
        return apiClient.userUpdate(
            user.id,
            user.email,
            user.firstName,
            user.lastName,
            user.proPar,
            user.civility,
            user.phoneNumber,
            user.siret,
            user.rSocial,
            user.photo
        )
    }

    override suspend fun sendActivationCode(userId: Int): SignupResponse {
        return apiClient.sendActivationCode(userId)
    }

    override suspend fun activateAccount(req: ActivateAccountRequest): SignupResponse {
        return apiClient.activateAccount(req)
    }

    override suspend fun reportUser(reportUserRequest: ReportUserRequest): ReportUserResponse {
        return apiClient.reportUser(
            reportUserRequest.userId,
            reportUserRequest.userRId,
            reportUserRequest.message
        )
    }

    override suspend fun setPushToken(pushTokenRequest: PushTokenRequest): TokenResponse {
        return apiClient.setPushToken(
            pushTokenRequest.userId,
            pushTokenRequest.token,
            pushTokenRequest.device
        )
    }

}
