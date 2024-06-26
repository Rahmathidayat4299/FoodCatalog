package com.course.core.utils

/**
 *hrahm,23/04/2024, 18:53
 **/
object Routes {
    const val LIST_SCREEN = "listScreen"
    const val DETAIL_SCREEN = "detailScreen/{${Values.IDVALUE}}"
    const val FAVORITE_SCREEN = "cartScreen"
    const val PROFILE_SCREEN = "profileScreen"
    fun getSecondScreenPath(idValue: Int?): String =
        // to avoid null and empty strings
        if (idValue != null) "detailScreen/$idValue" else "detailScreen/Empty"

    object Values {
        const val IDVALUE = "idValue"
    }
}