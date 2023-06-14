package com.clickandvisit.data.repository.imp

import com.clickandvisit.base.BaseRepository
import com.clickandvisit.data.repository.abs.PropertyRepository
import com.clickandvisit.data.retrofit.APIClient
import com.clickandvisit.global.helper.SharedPreferences
import javax.inject.Inject


class PropertyRepositoryImp @Inject constructor(
    apiClient: APIClient,
    sharedPreferences: SharedPreferences
) :
    BaseRepository(apiClient, sharedPreferences), PropertyRepository {

}
