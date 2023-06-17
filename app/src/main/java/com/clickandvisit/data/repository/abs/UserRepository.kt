package com.clickandvisit.data.repository.abs

import androidx.annotation.WorkerThread
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
import com.clickandvisit.global.enumeration.Optional
import okhttp3.MultipartBody


interface UserRepository {


    /** User **/

    @WorkerThread
    suspend fun isLoggedInWithDelay(time: Long): Optional<User>

    @WorkerThread
    suspend fun signUp(
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
    ): SignupResponse

    @WorkerThread
    suspend fun signInAndCache(email: String, password: String): SignupResponse

    @WorkerThread
    suspend fun activateAccount(code: String, userId: Int): SignupResponse

    @WorkerThread
    suspend fun sendActivationCode(userId: Int): SignupResponse

    @WorkerThread
    suspend fun getUser(): User

    @WorkerThread
    suspend fun userUpdate(user: User): User

    @WorkerThread
    suspend fun reportUser(reportUserRequest: ReportUserRequest): ReportUserResponse

    @WorkerThread
    suspend fun setPushToken(token: String): TokenResponse

    @WorkerThread
    suspend fun logout()

    fun isConnected(): Boolean


    /** Property **/

    suspend fun search(searchRequest: SearchRequest): SearchResponse

    suspend fun propertyDetails(propertyId: Int, userId: Int): PropertyDetailsResponse

    suspend fun addRemoveFavorite(favoriteRequest: FavoriteRequest): GlobalResponse

    suspend fun favoriteList(userId: Int): FavoritesResponse

    suspend fun createUpdateProperty(property: PropertyAdd): PropertyAddResponse

    suspend fun enableDisableProperty(propId: Int, enableDisable: Int): GlobalResponse

    suspend fun deleteSearch(searchId: Int): GlobalResponse

    suspend fun getSavedSearch(userId: Int): SavedSearchResponse


    /** Reservation **/

    suspend fun getAvailability(date: String, propId: Int): AvailabilityResponse

    suspend fun getAllReserved(propId: Int): ReservedPropertyResponse

    suspend fun reserve(userId: Int, propertyId: Int, dateTime: String): ReservationResponse

    suspend fun setAvailability(
        userId: Int,
        propertyId: Int,
        dateTime: String,
        removeAdd: String
    ): ResultModel


    suspend fun acceptRefuseReservation(
        propertyId: Int,
        reservationId: Int,
        accept: Boolean
    ): ReservationResponse


    suspend fun getReservations(userId: Int, accept: Boolean): ReservationResponse


    /** Chat **/

    suspend fun contactOwner(userId: Int, propertyId: Int, message: String): ContactOwnerResponse

    suspend fun sendMessage(userId: Int, discussionId: Int, message: String): GlobalResponse

    suspend fun getDiscussions(): DiscussionsResponse

    suspend fun getMessages(discussionId: Int): MessagesResponse

    fun getCurrentUserId(): Int

}
