package com.clickandvisit.data.repository.abs

import androidx.annotation.WorkerThread
import com.clickandvisit.data.model.user.User
import com.clickandvisit.global.enumeration.Optional

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
