package com.foodline.data.repository.imp

import androidx.annotation.WorkerThread
import com.foodline.base.BaseRepository
import com.foodline.data.db.Database
import com.foodline.data.model.user.User
import com.foodline.data.repository.abs.UserRepository
import com.foodline.data.retrofit.APIClient
import com.foodline.global.enumeration.Optional
import com.foodline.global.helper.SharedPreferences
import kotlinx.coroutines.delay
import javax.inject.Inject


class UserRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences,
    database: Database
) :
    BaseRepository(apiClient, sharedPreferences, database), UserRepository {

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
