package com.minipayhq.minipayandroidsdk.internal.service

import com.minipayhq.minipayandroidsdk.internal.model.request.AuthorizedAppAddRequest
import com.minipayhq.minipayandroidsdk.internal.model.request.AuthorizedAppPostUsageEventRequest
import com.minipayhq.minipayandroidsdk.internal.model.request.LoginRequest
import com.minipayhq.minipayandroidsdk.internal.model.response.AuthorizedAppAddResponseWrapper
import com.minipayhq.minipayandroidsdk.internal.model.response.AuthorizedAppPostUsageEventResponseWrapper
import com.minipayhq.minipayandroidsdk.internal.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

internal interface MinipayService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("apps/add")
    fun addApp(@Body request: AuthorizedAppAddRequest): Call<AuthorizedAppAddResponseWrapper>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("security/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("apps/usage")
    fun postUsageEvent(
        @Body request: AuthorizedAppPostUsageEventRequest,
        @Header("X-API-Key") apiKey: String
    ): Call<AuthorizedAppPostUsageEventResponseWrapper>
}
