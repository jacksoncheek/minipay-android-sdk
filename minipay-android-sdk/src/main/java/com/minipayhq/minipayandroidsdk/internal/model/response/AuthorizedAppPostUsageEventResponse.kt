package com.minipayhq.minipayandroidsdk.internal.model.response

import com.squareup.moshi.Json

internal data class AuthorizedAppPostUsageEventResponse(

    @Json(name="userId")
    val userId: String,

    @Json(name="authorized")
    val authorized: Boolean
)

internal data class AuthorizedAppPostUsageEventResponseWrapper(

    @Json(name="response")
    val response: AuthorizedAppPostUsageEventResponse
)