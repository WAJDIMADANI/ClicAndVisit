package com.clickandvisit.global.helper

sealed class Navigation {

    object Back : Navigation()

    object SignInActivityNavigation : Navigation()

    object SignUpActivityNavigation : Navigation()

    object ResetPasswordActivityNavigation : Navigation()

    object OtpActivityNavigation : Navigation()

    object IntroActivityNavigation : Navigation()

    object HomeActivityNavigation : Navigation()

    object MenuActivityNavigation : Navigation()

    object HelpActivityNavigation : Navigation()

    object TransportActivityNavigation : Navigation()

    object SendActivityNavigation : Navigation()

    object ProfileActivityNavigation : Navigation()

    object PaymentActivityNavigation : Navigation()

    object AlertsActivityNavigation : Navigation()

    object ChatActivityNavigation : Navigation()

    object AdsActivityNavigation : Navigation()

    object PackageActivityNavigation : Navigation()

    object FilterActivityNavigation : Navigation()

    object DetailsActivityNavigation : Navigation()


    object GalleryNavigation : Navigation()

    data class CameraNavigation(val imageName: String) : Navigation()

    object OpenDrawerNavigation : Navigation()

}