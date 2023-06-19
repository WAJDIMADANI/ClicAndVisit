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

}