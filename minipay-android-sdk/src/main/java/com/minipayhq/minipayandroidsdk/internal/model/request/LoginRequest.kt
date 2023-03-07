package com.minipayhq.minipayandroidsdk.internal.model.request

import com.minipayhq.minipayandroidsdk.internal.model.Credentials
import com.squareup.moshi.Json

internal data class LoginRequest(

    @Json(name="credentials")
    val credentials: Credentials
)
