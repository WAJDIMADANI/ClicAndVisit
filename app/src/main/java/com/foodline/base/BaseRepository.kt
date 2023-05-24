package com.foodline.base


import com.foodline.data.db.Database
import com.foodline.data.retrofit.APIClient
import com.foodline.global.helper.SharedPreferences

/**
 * this is the base repository class, all project repositories should extends this class.
 */
abstract class BaseRepository(
    protected val apiClient: APIClient,
    protected val sharedPreferences: SharedPreferences,
    protected val database: Database
)
