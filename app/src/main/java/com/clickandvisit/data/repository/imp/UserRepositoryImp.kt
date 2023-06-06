package com.clickandvisit.data.repository.imp

import androidx.annotation.WorkerThread
import com.clickandvisit.base.BaseRepository
import com.clickandvisit.data.model.user.User
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
    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): User {
        val response = apiClient.signUpAndCache(email, password, firstName, lastName)
        sharedPreferences.saveUser(response.data)
        return response.data
    }

    @WorkerThread
    override suspend fun signInAndCache(email: String, password: String): User {
        val response = apiClient.signIn(email, password)
        sharedPreferences.saveUser(response.data)
        return response.data
    }

    @WorkerThread
    override suspend fun isLoggedInWithDelay(time: Long): Optional<User> {
        delay(time)
        return if (sharedPreferences.isConnected()) {
            Optional.Some(sharedPreferences.getUser())
        } else {
            Optional.None
        }
    }

    override suspend fun setIntro() {
        sharedPreferences.setIntro(true)
    }

    override suspend fun isIntro(): Boolean {
        return sharedPreferences.isIntro()
    }

}
