package com.foodline.data.repository.abs

import androidx.annotation.WorkerThread
import com.foodline.data.model.user.User
import com.foodline.global.enumeration.Optional

interface UserRepository {

    @WorkerThread
    suspend fun isLoggedInWithDelay(time: Long): Optional<User>

    @WorkerThread
    suspend fun signInAndCache(email: String, password: String): User

    @WorkerThread
    suspend fun signUp(firstName: String, lastName: String, email: String, password: String): User

    suspend fun setIntro()

    suspend fun isIntro():Boolean

}
