package com.clickandvisit.global.utils

object ExtraKeys {

    class HomeActivity {
        companion object {
            const val HOME_EXTRA_USER_KEY: String = "home_extra_user"
        }
    }

    class ThreeFragment {
        companion object {
            const val THREE_EXTRA_ARG_KEY: String = "three_fragment_extra_arg"
        }
    }

    class FourFragment {
        companion object {
            const val FOUR_EXTRA_FIRST_ARG_KEY: String = "var1"
            const val FOUR_EXTRA_SECOND_ARG_KEY: String = "var2"
        }
    }

    class NewsActivity {
        companion object {
            const val NEWS_EXTRA_VAR_KEY: String = "var1"
        }
    }


    class DetailsActivity {
        companion object {
            const val DETAILS_EXTRA_KEY_FROM_PACK: String = "details_extra_pack"
            const val DETAILS_EXTRA_KEY_FROM_ADS: String = "details_extra_ads"
        }
    }

}