package com.minipayhq.minipayandroidsdk.internal.model.request

import com.squareup.moshi.Json

internal data class AuthorizedAppAddRequest(

    @Json(name="customUserId")
    val customUserId: String,

    @Json(name="planId")
    val planId: String,

    @Json(name="minipayToken")
    val minipayToken: String
)
