package com.clickandvisit.data.repository.imp

import androidx.annotation.WorkerThread
import com.clickandvisit.base.BaseRepository
import com.clickandvisit.data.model.GlobalResponse
import com.clickandvisit.data.model.chat.ContactOwnerResponse
import com.clickandvisit.data.model.chat.DiscussionsResponse
import com.clickandvisit.data.model.chat.MessagesResponse
import com.clickandvisit.data.model.property.*
import com.clickandvisit.data.model.property.add.PropertyAdd
import com.clickandvisit.data.model.property.add.PropertyAddResponse
import com.clickandvisit.data.model.reservation.*
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.data.retrofit.APIClient
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.SharedPreferences
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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
        phoneNumber: String,
        file: MultipartBody.Part
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
            // ,file
        )
    }

    @WorkerThread
    override suspend fun signInAndCache(email: String, password: String): SignupResponse {
        val response = apiClient.signIn(email, password)
        if (response.result) {
            sharedPreferences.saveUser(response.user)
        }
        return response
    }

    override suspend fun activateAccount(code: String, userId: Int): SignupResponse {
        val response = apiClient.activateAccount(
            userId,
            code
        )
        sharedPreferences.saveUser(response.user)
        return response
    }

    override suspend fun sendActivationCode(userId: Int): SignupResponse {
        return apiClient.sendActivationCode(userId)
    }


    override suspend fun getUser(): User {
        val updatedUser = apiClient.getUser(sharedPreferences.getUser().id.toInt())
        sharedPreferences.saveUser(updatedUser.user)
        return updatedUser.user
    }

    override fun getSharedUser(): User {
        return sharedPreferences.getUser()
    }

    override suspend fun userUpdate(user: User): User {
        val userResponse = apiClient.userUpdate(
            user.id,
            user.email,
            user.firstName,
            user.lastName,
            user.proPar,
            user.civility,
            user.phoneNumber,
            user.siret,
            user.rSocial
        )
        sharedPreferences.saveUser(userResponse.user)
        return userResponse.user
    }

    override suspend fun userUpdate(id: RequestBody, file: MultipartBody.Part): User {
        val userResponse = apiClient.userUpdate(
            id,
            file
        )
        sharedPreferences.saveUser(userResponse.user)
        return userResponse.user
    }


    override suspend fun reportUser(userTRId: Int, message: String): Void {
        return apiClient.reportUser(
            sharedPreferences.getUser().id.toInt(),
            userTRId,
            message
        )
    }

    override suspend fun setPushToken(token: String): TokenResponse {
        return apiClient.setPushToken(
            sharedPreferences.getUser().id.toInt(),
            token,
            "android"
        )
    }

    override suspend fun logout() {
        sharedPreferences.clearCache()
    }

    override suspend fun removeAccount(): GlobalResponse {
        return apiClient.removeAccount(sharedPreferences.getUser().id.toInt())
    }

    override fun isConnected(): Boolean {
        return sharedPreferences.isConnected()
    }

    /** Property **/

    override suspend fun search(sortBy: String?, sortHow: String?): SearchResponse {
        return apiClient.search(sortBy, sortHow)
    }

    override suspend fun getMyProperty(): SearchResponse {
        return apiClient.search(sharedPreferences.getUser().id.toInt())
    }

    override suspend fun search(searchRequest: SearchRequest): SearchResponse {
        var category = ""

        if (searchRequest.category.isNullOrEmpty()) {
            category = ""
        } else if (searchRequest.category?.size == 1) {
            category = searchRequest.category?.get(0).toString()
        } else {
            searchRequest.category?.forEach {
                category = category.plus(it.toString())
                category = category.plus(",")
            }
            category = category.substring(0, category.length - 1)
        }

        return apiClient.search(
            searchRequest.adsType,
            category,
            searchRequest.minRooms,
            searchRequest.maxRooms,
            searchRequest.minArea,
            searchRequest.maxArea,
            searchRequest.minPrice,
            searchRequest.maxPrice,
            searchRequest.favoriteUserId,
            searchRequest.saveSearch,
            searchRequest.address,
            searchRequest.sortBy,
            searchRequest.sortHow
        )
    }

    override suspend fun propertyDetails(propertyId: Int): PropertyDetailsResponse {
        return apiClient.propertyDetails(sharedPreferences.getUser().id.toInt(), propertyId)
    }

    override suspend fun addRemoveFavorite(favoriteRequest: FavoriteRequest): GlobalResponse {
        return apiClient.addRemoveFavorite(
            favoriteRequest.propertyId,
            sharedPreferences.getUser().id.toInt(),
            favoriteRequest.action
        )
    }

    override suspend fun favoriteList(): FavoritesResponse {
        return apiClient.favoriteList(sharedPreferences.getUser().id.toInt())
    }

    override suspend fun createUpdateProperty(property: PropertyAdd): PropertyAddResponse {
        return apiClient.createUpdateProperty(
            sharedPreferences.getUser().id.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propId?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propType?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propCategory?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propSurface?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propPrix?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propEtage?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propEtageSur?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propEnery?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propGes?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.infoComp?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_chambres?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_suites?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_salles_de_bains?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_salles_d_eau?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_bureaux?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_dressing?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_garages?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_caves?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_balcons?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_terrasse?.toRequestBody("text/plain".toMediaTypeOrNull()),
            // property.prop_meta_surface_terrain,
            property.prop_meta_annee?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_piscine?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_piscinable?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_pool_house?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_sans_vis_a_vis?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_ascenseur?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_duplex?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_triplex?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_meta_rez_de_jardin?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_localisation_ville?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_localisation_codepostal?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.prop_localisation_complement_adresse?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.proInterphoneName?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propCodeportail?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propInfos?.toRequestBody("text/plain".toMediaTypeOrNull()),
            property.propMainPhoto,
            property.propAlbumPhoto1,
            property.propAlbumPhoto2,
            property.propAlbumPhoto3,
            property.propAlbumPhoto4,
            property.propAlbumPhoto5,
            property.propAlbumPhoto6
        )
    }

    override suspend fun downloadFile(fileUrl: String): Response<ResponseBody> {
        return apiClient.downloadFile(fileUrl)
    }

    override suspend fun getSavedSearch(): SavedSearchResponse {
        return apiClient.getSavedSearch(sharedPreferences.getUser().id.toInt())
    }

    override suspend fun enableDisableProperty(
        propId: Int
    ): GlobalResponse {
        return apiClient.enableDisableProperty(propId, 0)
    }

    override suspend fun deleteSearch(searchId: Int): GlobalResponse {
        return apiClient.deleteSearch(searchId)
    }

    override suspend fun getAvailability(date: String, propId: Int): AvailabilityResponse {
        return apiClient.getAvailability(date, propId)
    }

    override suspend fun getAllReserved(propId: Int): ReservedPropertyResponse {
        return apiClient.getAllReserved(propId)
    }

    override suspend fun reserve(
        propertyId: Int,
        dateTime: String
    ): ReserveResponse {
        return apiClient.reserve(sharedPreferences.getUser().id.toInt(), propertyId, dateTime)
    }

    override suspend fun setAvailability(
        propertyId: Int,
        dateTime: String,
        removeAdd: String
    ): ResultModel {
        return apiClient.setAvailability(
            sharedPreferences.getUser().id.toInt(),
            propertyId,
            dateTime,
            removeAdd
        )
    }

    override suspend fun acceptRefuseReservation(
        propertyId: Int,
        reservationId: Int,
        accept: Boolean
    ): ReservationResponse {
        return apiClient.acceptRefuseReservation(propertyId, reservationId, accept)
    }

    override suspend fun getReservations(accept: Boolean): ReservationResponse {
        return apiClient.getReservations(sharedPreferences.getUser().id.toInt(), accept)
    }

    override suspend fun contactOwner(
        propertyId: Int,
        message: String
    ): Void {
        return apiClient.contactOwner(sharedPreferences.getUser().id.toInt(), propertyId, message)
    }

    override suspend fun sendMessage(
        discussionId: Int,
        message: String
    ): GlobalResponse {
        return apiClient.sendMessage(sharedPreferences.getUser().id.toInt(), discussionId, message)
    }

    override suspend fun getDiscussions(): DiscussionsResponse {
        return apiClient.getDiscussions(sharedPreferences.getUser().id.toInt())
    }

    override suspend fun getMessages(discussionId: Int): MessagesResponse {
        return apiClient.getMessages(sharedPreferences.getUser().id.toInt(), discussionId)
    }

    override fun getCurrentUserId(): Int {
        return sharedPreferences.getUser().id.toInt()
    }
}
