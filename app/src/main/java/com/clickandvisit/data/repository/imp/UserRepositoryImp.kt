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
import com.clickandvisit.data.model.reservation.AvailabilityResponse
import com.clickandvisit.data.model.reservation.ReservationResponse
import com.clickandvisit.data.model.reservation.ReservedPropertyResponse
import com.clickandvisit.data.model.reservation.ResultModel
import com.clickandvisit.data.model.user.*
import com.clickandvisit.data.model.user.signup.SignupResponse
import com.clickandvisit.data.repository.abs.UserRepository
import com.clickandvisit.data.retrofit.APIClient
import com.clickandvisit.global.enumeration.Optional
import com.clickandvisit.global.helper.SharedPreferences
import kotlinx.coroutines.delay
import okhttp3.MultipartBody
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
            user.rSocial,
            user.photo
        )
        sharedPreferences.saveUser(userResponse.user)
        return userResponse.user
    }


    override suspend fun reportUser(reportUserRequest: ReportUserRequest): ReportUserResponse {
        return apiClient.reportUser(
            reportUserRequest.userId,
            reportUserRequest.userRId,
            reportUserRequest.message
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

    override fun isConnected(): Boolean {
        return sharedPreferences.isConnected()
    }

    /** Property **/

    override suspend fun search(): SearchResponse {
        return apiClient.search()
    }

    override suspend fun search(searchRequest: SearchRequest): SearchResponse {
        return apiClient.search(
            searchRequest.adsType,
            searchRequest.category,
            searchRequest.minRooms,
            searchRequest.maxRooms,
            searchRequest.minArea,
            searchRequest.maxArea,
            searchRequest.minPrice,
            searchRequest.maxPrice,
            searchRequest.favoriteUserId,
            //sharedPreferences.getUser().id.toInt(),
            searchRequest.saveSearch,
            searchRequest.address,
            searchRequest.sortBy,
            searchRequest.sortHow
        )
    }

    override suspend fun propertyDetails(propertyId: Int): PropertyDetailsResponse {
        return apiClient.propertyDetails(propertyId, sharedPreferences.getUser().id.toInt())
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
            sharedPreferences.getUser().id.toInt(),
            property.propId,
            property.propType,
            property.propCategory,
            property.propSurface,
            property.propPrix,
            property.propEtage,
            property.propEtageSur,
            property.propEnery,
            property.propGes,
            property.infoComp,
            property.prop_meta_chambres,
            property.prop_meta_suites,
            property.prop_meta_salles_de_bains,
            property.prop_meta_salles_d_eau,
            property.prop_meta_bureaux,
            property.prop_meta_dressing,
            property.prop_meta_garages,
            property.prop_meta_caves,
            property.prop_meta_balcons,
            property.prop_meta_terrasse,
           // property.prop_meta_surface_terrain,
            property.prop_meta_annee,
            property.prop_meta_piscine,
            property.prop_meta_piscinable,
            property.prop_meta_pool_house,
            property.prop_meta_sans_vis_a_vis,
            property.prop_meta_ascenseur,
            property.prop_meta_duplex,
            property.prop_meta_triplex,
            property.prop_meta_rez_de_jardin,
            property.prop_localisation_ville,
            property.prop_localisation_codepostal,
            property.prop_localisation_complement_adresse,
            property.proInterphoneName,
            property.propCodeportail,
            property.propInfos,
            property.propMainPhoto,
            property.propAlbumPhoto1,
            property.propAlbumPhoto2,
            property.propAlbumPhoto3
        )
    }

    override suspend fun enableDisableProperty(propId: Int, enableDisable: Int): GlobalResponse {
        return apiClient.enableDisableProperty(propId, enableDisable)
    }

    override suspend fun deleteSearch(searchId: Int): GlobalResponse {
        return apiClient.deleteSearch(searchId)
    }

    override suspend fun getSavedSearch(): SavedSearchResponse {
        return apiClient.getSavedSearch(sharedPreferences.getUser().id.toInt())
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
    ): ReservationResponse {
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
    ): ContactOwnerResponse {
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
