package com.clickandvisit.base


import com.clickandvisit.data.db.Database
import com.clickandvisit.data.retrofit.APIClient
import com.clickandvisit.global.helper.SharedPreferences

/**
 * this is the base repository class, all project repositories should extends this class.
 */
abstract class BaseRepository(
    protected val apiClient: APIClient,
    protected val sharedPreferences: SharedPreferences,
    protected val database: Database
)
