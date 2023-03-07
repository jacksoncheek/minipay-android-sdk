package com.minipayhq.minipayandroidsdk.internal.model.response

import com.squareup.moshi.Json

internal data class AuthorizedAppAddResponse(

    @Json(name="successful")
    val successful: Boolean
)

internal data class AuthorizedAppAddResponseWrapper(

    @Json(name="response")
    val response: AuthorizedAppAddResponse
)