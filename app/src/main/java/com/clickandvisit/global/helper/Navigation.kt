package com.clickandvisit.global.helper

sealed class Navigation {

    object Back : Navigation()

    object SignInActivityNavigation : Navigation()

    object SignUpActivityNavigation : Navigation()

    object ResetPasswordActivityNavigation : Navigation()

    object OtpActivityNavigation : Navigation()

    object IntroActivityNavigation : Navigation()

    object HomeActivityNavigation : Navigation()

    object GalleryNavigation : Navigation()

    data class CameraNavigation(val imageName: String) : Navigation()

    object OpenDrawerNavigation : Navigation()

}