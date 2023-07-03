package com.clickandvisit.global.helper

import com.clickandvisit.data.model.property.Property
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.data.model.property.SearchResponse

sealed class Navigation {

    object Back : Navigation()

    object SignInActivityNavigation : Navigation()

    data class MapsActivityNavigation(val searchResponse: SearchResponse) : Navigation()

    data class ShareNavigation(val property: Property) : Navigation()

    data class FilterActivityNavigation(val searchRequest: SearchRequest?) : Navigation()

    object SignUpActivityNavigation : Navigation()

    object ResetPasswordActivityNavigation : Navigation()

    data class OtpActivityNavigation(val userId: Int) : Navigation()

    object ProfileActivityNavigation : Navigation()

    object SearchActivityNavigation : Navigation()

    object ChatActivityNavigation : Navigation()

    data class ConvActivityNavigation(val discId: Int) : Navigation()

    object HomeActivityNavigation : Navigation()

    data class AddAdsActivity(val value: Property) : Navigation()

    object GalleryNavigation : Navigation()

    data class CameraNavigation(val imageName: String) : Navigation()

    data class PopupNavigation(
        val index: Int,
        val title: String,
        val hint: String,
        val inputType: Int,
        val oldValue: String?
    ) : Navigation()

    object OpenDrawerNavigation : Navigation()

    object CalendarFragmentNavigation : Navigation()

    object RateNav : Navigation()

    data class Phone(val phoneNumber: String) : Navigation()

    data class AdsDetailsActivityNavigation(val value: Property) : Navigation()

    data class DPENavigation(val energy: String) : Navigation()

    data class GESNavigation(val ges: String) : Navigation()

}