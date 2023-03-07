package com.minipayhq.minipayandroidsdk.internal.model.response

import com.minipayhq.minipayandroidsdk.internal.model.User
import com.squareup.moshi.Json

internal data class LoginResponse(

    @Json(name="user")
    val user: User,

    @Json(name="token")
    val token: String
)
