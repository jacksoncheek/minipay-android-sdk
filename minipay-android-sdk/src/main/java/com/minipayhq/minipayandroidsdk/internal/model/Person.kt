package com.minipayhq.minipayandroidsdk.internal.model

import com.squareup.moshi.Json

internal data class Person(

    @Json(name="lastName")
    val firstName: String,

    @Json(name="lastName")
    val lastName: String
)
