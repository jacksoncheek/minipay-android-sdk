package com.minipayhq.minipayandroidsdk.internal.model

import com.squareup.moshi.Json

internal data class Credentials(

    @Json(name="email")
    val email: String,

    @Json(name="password")
    val password: String
)
