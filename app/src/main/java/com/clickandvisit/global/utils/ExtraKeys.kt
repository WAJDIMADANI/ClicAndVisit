package com.clickandvisit.global.utils

object ExtraKeys {

    class HomeActivity {
        companion object {
            const val HOME_EXTRA_USER_KEY: String = "home_extra_user"
        }
    }

    class OtpActivity{
        companion object {
            const val USER_ID_EXTRA_KEY: String = "user_id_extra_key"
        }
    }

    class ConvActivity{
        companion object {
            const val DISC_ID_EXTRA_KEY: String = "disc_id_extra_key"
        }
    }

    class MapsActivity{
        companion object {
            const val SEARCH_EXTRA_KEY: String = "search_extra_key"
        }
    }

    class FilterActivity{
        companion object {
            const val SEARCH_REQ_EXTRA_KEY: String = "search_req_extra_key"
            const val SEARCH_REQ_CODE: Int = 111
        }
    }


    class AddAdsActivity{
        companion object {
            const val PROPERTY_EXTRA_KEY_EDIT: String = "prop_extra_edit_extra_key"
            const val PROPERTY_EXTRA_KEY_MEET: String = "prop_extra_meet_extra_key"
            const val PROPERTY_EXTRA_KEY_PROP: String = "prop_extra_edit"
        }
    }

    class HomeNotificationKeys {
        companion object {
            const val HOME_NOTIFICATION_EXTRA_KEY = "home_notification_extra_key"
            //const val HOME_NOTIFICATION_EXTRA_VALUE = "home_notification_extra_value"
        }
    }
}