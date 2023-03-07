package com.minipayhq.minipayandroidsdk.internal.model

import com.squareup.moshi.Json

internal data class User(

    @Json(name="credentials")
    val credentials: Credentials,

    @Json(name="person")
    val person: Person,

    @Json(name="userId")
    val userId: String
)
