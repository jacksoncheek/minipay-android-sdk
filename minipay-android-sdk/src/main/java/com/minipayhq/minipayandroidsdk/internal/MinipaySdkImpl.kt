package com.minipayhq.minipayandroidsdk.internal

import android.content.Context
import com.minipayhq.minipayandroidsdk.MinipaySdk
import com.minipayhq.minipayandroidsdk.MinipaySdkAddAppResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkLoginResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkPostUsageEventResultCallback
import com.minipayhq.minipayandroidsdk.MinipaySdkResult
import com.minipayhq.minipayandroidsdk.internal.model.request.AuthorizedAppAddRequest
import com.minipayhq.minipayandroidsdk.internal.model.request.AuthorizedAppPostUsageEventRequest
import com.minipayhq.minipayandroidsdk.internal.model.response.AuthorizedAppAddResponseWrapper
import com.minipayhq.minipayandroidsdk.internal.model.response.AuthorizedAppPostUsageEventResponseWrapper
import com.minipayhq.minipayandroidsdk.internal.service.MinipayService
import com.minipayhq.minipayandroidsdk.internal.state.MinipaySdkStateRepo
import com.minipayhq.minipayandroidsdk.internal.ui.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MinipaySdkImpl(private val params: Params) : MinipaySdk {

    init {
        Graph.Builder()
            .apiKey(apiKey = params.apiKey)
            .minipayService(minipayService = params.minipayService)
            .stateRepo(stateRepo = MinipaySdkStateRepo())
            .build()
    }

    override fun authorizeApp(
        customUserId: String,
        planId: String,
        minipayToken: String,
        callback: MinipaySdkAddAppResultCallback
    ) {
        Graph.instance.stateRepo.addAppResultCallback = callback

        val request = AuthorizedAppAddRequest(
            customUserId = customUserId,
            planId = planId,
            minipayToken = minipayToken
        )

        params.minipayService.addApp(request = request).enqueue(object :
            Callback<AuthorizedAppAddResponseWrapper> {
            override fun onResponse(call: Call<AuthorizedAppAddResponseWrapper>, response: Response<AuthorizedAppAddResponseWrapper>) {
                if (!response.isSuccessful) {
                    callback(MinipaySdkResult.Failure(error = "App authorization failed"))
                    return
                }

                val body = response.body()

                if (body == null) {
                    callback(MinipaySdkResult.Failure(error = "App authorization failed"))
                    return
                }

                callback(MinipaySdkResult.Success(result = body.response.successful))
            }

            override fun onFailure(call: Call<AuthorizedAppAddResponseWrapper>, t: Throwable) {
                callback(MinipaySdkResult.Failure(error = t.message ?: "App authorization failed"))
            }
        })
    }

    override fun login(
        appContext: Context,
        callback: MinipaySdkLoginResultCallback
    ) {
        Graph.instance.stateRepo.loginResultCallback = callback

        LoginActivity.Args().launch(appContext)
    }

    override fun postUsageEvent(
        customUserId: String,
        planId: String,
        callback: MinipaySdkPostUsageEventResultCallback
    ) {
        Graph.instance.stateRepo.postUsageEventResultCallback = callback

        val request = AuthorizedAppPostUsageEventRequest(
            customUserId = customUserId,
            planId = planId
        )

        params.minipayService.postUsageEvent(request = request, apiKey = params.apiKey).enqueue(object :
            Callback<AuthorizedAppPostUsageEventResponseWrapper> {
            override fun onResponse(call: Call<AuthorizedAppPostUsageEventResponseWrapper>, response: Response<AuthorizedAppPostUsageEventResponseWrapper>) {
                if (!response.isSuccessful) {
                    callback(MinipaySdkResult.Failure(error = "Post usage event failed"))
                    return
                }

                val body = response.body()

                if (body == null) {
                    callback(MinipaySdkResult.Failure(error = "Post usage event failed"))
                    return
                }

                callback(MinipaySdkResult.Success(result = body.response.authorized))
            }

            override fun onFailure(call: Call<AuthorizedAppPostUsageEventResponseWrapper>, t: Throwable) {
                callback(MinipaySdkResult.Failure(error = t.message ?: "Post usage event failed"))
            }
        })
    }

    data class Params(
        val apiKey: String,
        val minipayService: MinipayService
    )
}
